package il.org.spartan.plugin.preferences.revision;

import static java.util.stream.Collectors.*;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jdt.core.dom.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import fluent.ly.*;
import il.org.spartan.plugin.preferences.revision.PreferencesResources.*;
import il.org.spartan.spartanizer.tippers.*;
import il.org.spartan.spartanizer.tipping.*;
import il.org.spartan.spartanizer.traversal.*;
import il.org.spartan.utils.*;

/** Support for plugin's XML configurations file for projects. Currently
 * describes what tippers are enabled for the project.
 * @author Ori Roth {@code ori.rothh@gmail.com}
 * @since 2017-02-01 */
public class XMLSpartan {
  public static final String FILE_NAME = "spartanizer.xml";
  public static final String BASE = "spartan";
  public static final String CURRENT_VERSION = "5.0";
  public static final Examples EMPTY_PREVIEW = new Examples()//
      .convert("[no available preview]")//
      .to("[no available preview]");
  public static final String ENABLED = "enabled";
  public static final String KIND = "kind";
  public static final String VALUE = "value";
  private static final Collection<Class<? extends Tipper<? extends ASTNode>>> NON_CORE = //
      new HashSet<>(as.list(//
          CatchClauseRenameParameterToIt.class, //
          EnhancedForParameterRenameToIt.class, //
          ForRenameInitializerToIt.class, //
          ForToForUpdaters.class, //
          InfixExpressionConcatentateCompileTime.class, //
          LambdaRenameSingleParameterToLambda.class, //
          MethodDeclarationRenameReturnToDollar.class, //
          MethodDeclarationRenameSingleParameter.class, //
          MethodInvocationToStringToEmptyStringAddition.class, //
          ModifierRedundant.class, //
          ParameterAnonymize.class, //
          ForParameterRenameToIt.class, //
          LambdaRenameSingleParameterToLambda.class, //
          ConstructorEmptyRemove.class, //
          ReturnDeadAssignment.class //
      ));
  public static final String TIPPER = "tipper";
  public static final String NOTATION = "notation";
  public static final String TIPPER_ID = "id";
  public static final String VERSION = "version";

  /** Computes enabled tippers for project. If some error occur (such as a
   * corrupted XML file), full tippers collection is returned.
   * @param p JD
   * @return enabled tippers for project */
  public static Set<Class<Tipper<? extends ASTNode>>> enabledTippers(final IProject p) {
    final Set<Class<Tipper<? extends ASTNode>>> $ = //
        Configurations.allTippers()//
            .map(λ -> λ.getClass())//
            .map(XMLSpartan::unchecked)//
            .collect(toSet());
    if (p == null)
      return $;
    final Set<String> ets = getTippersByCategories(p)//
        .values()//
        .stream()//
        .flatMap(Arrays::stream)//
        .filter(SpartanElement::enabled)//
        .map(SpartanElement::name)//
        .collect(toSet());
    $.removeIf(λ -> !ets.contains(λ.getSimpleName()));
    return $;
  }

  /** Computes enabled tippers by categories for the project. If some error
   * occur (such as a corrupted XML file), an empty map is returned.
   * @param p JD
   * @return enabled tippers for the project */
  public static Map<SpartanCategory, SpartanTipper[]> getTippersByCategories(final IProject p) {
    final Map<SpartanCategory, SpartanTipper[]> $ = new HashMap<>();
    final Document d = getFile(p);
    if (d == null)
      return $;
    final NodeList ns = d.getElementsByTagName(TIPPER);
    if (ns == null)
      return $;
    final Map<TipperGroup, SpartanCategory> tcs = new HashMap<>();
    final Map<TipperGroup, List<SpartanTipper>> tgs = new HashMap<>();
    for (int i = 0; i < ns.getLength(); ++i) {
      final Element e = (Element) ns.item(i);
      final Class<?> tc = Tippers.cache.serivalVersionUIDToTipper.get(e.getAttribute(TIPPER_ID));
      if (tc == null)
        continue;
      final String description = Tippers.cache.tipperToDescription.get(tc);
      final Examples preview = Tippers.cache.tipperToExamples.get(tc);
      final TipperGroup g = Tippers.cache.tipperClassToTipperInstance.get(tc).tipperGroup();
      if (!tgs.containsKey(g)) {
        tgs.put(g, an.empty.list());
        tcs.put(g, new SpartanCategory(g.name(), false));
      }
      final SpartanTipper st = new SpartanTipper(//
          tc.getSimpleName(), //
          Boolean.parseBoolean(e.getAttribute(ENABLED)), //
          tcs.get(g), description != null ? description : "No description available", //
          preview != null ? preview : EMPTY_PREVIEW);
      tcs.get(g).addChild(st);
      tgs.get(g).add(st);
    }
    tgs.forEach((key, value) -> $.put(tcs.get(key), value.toArray(new SpartanTipper[value.size()])));
    return $;
  }

  /** Updates the project's XML file to enable given tippers.
   * @param p JD
   * @param ss enabled tippers by name */
  public static void updateEnabledTippers(final IProject p, final Collection<String> ss) {
    final Document d = getFile(p);
    if (d == null)
      return;
    final NodeList ns = d.getElementsByTagName(TIPPER);
    if (ns == null)
      return;
    for (int i = 0; i < ns.getLength(); ++i) {
      final Element e = (Element) ns.item(i);
      final String nameByID = Tippers.cache.idToNameOriWhatsThisFindAGoodName.get(e.getAttribute(TIPPER_ID));
      if (nameByID != null)
        e.setAttribute(ENABLED, ss.contains(nameByID) ? "true" : "false");
    }
    commit(p, d);
  }

  /** Writes XML dom object to file.
   * @param f JD
   * @param d JD
   * @return true iff the operation has been completed successfully */
  private static boolean commit(final IFile f, final Document d) {
    final Source domSource = new DOMSource(d);
    final StringWriter writer = new StringWriter();
    final Result result = new StreamResult(writer);
    final TransformerFactory tf = TransformerFactory.newInstance();
    try {
      final Transformer t = tf.newTransformer();
      t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
      t.setOutputProperty(OutputKeys.METHOD, "xml");
      t.setOutputProperty(OutputKeys.INDENT, "yes");
      t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      t.setOutputProperty(OutputKeys.STANDALONE, "yes");
      t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
      t.transform(domSource, result);
      f.setContents(new ByteArrayInputStream((writer + "").getBytes()), false, false, new NullProgressMonitor());
      return true;
    } catch (CoreException | TransformerException ¢) {
      note.bug(¢);
      return false;
    }
  }

  /** Writes XML dom object to project.
   * @param p JD
   * @param d JD
   * @return true iff the operation has been completed successfully */
  static boolean commit(final IProject p, final Document d) {
    final IFile $ = p.getFile(FILE_NAME);
    return $ != null && $.exists() && commit($, d);
  }

  /** Adds a new tipper to the XML document.
   * @param d JD
   * @param p JD
   * @param seen seen tippers by name. Tippers can appear multiple times in the
   *        {@link Configuration}, so we should avoid duplications
   * @param e base element */
  private static void createEnabledNodeChild(final Document d, final Tipper<?> t, final Collection<String> seen, final Node e) {
    if (d == null || t == null || seen == null || e == null)
      return;
    final String n = t.tipperName();
    if (seen.contains(n))
      return;
    final Element $ = d.createElement(TIPPER);
    if ($ == null)
      return;
    $.setAttribute(ENABLED, !NON_CORE.contains(t.getClass()) + "");
    $.setAttribute(TIPPER_ID, ObjectStreamClass.lookup(t.getClass()).getSerialVersionUID() + "");
    seen.add(n);
    e.appendChild($);
  }

  /** Adds a new notation to the XML document.
   * @param d JD
   * @param kind JD
   * @param value JD
   * @param seen seen tippers by name. Tippers can appear multiple times in the
   *        {@link Configuration}, so we should avoid duplications
   * @param n base element */
  private static void createNotationChild(final Document d, final String kind, final String value, final Collection<String> seen, final Node n) {
    if (d == null || kind == null || seen == null || value == null || seen.contains(kind) || n == null)
      return;
    final Element $ = d.createElement(NOTATION);
    if ($ == null)
      return;
    $.setAttribute(KIND, kind);
    $.setAttribute(VALUE, value);
    seen.add(kind);
    n.appendChild($);
  }

  public static Document getXML(final IProject $) {
    return getFile($);
  }

  /** Return XML file for given project. Creates one if absent.
   * @param p JD
   * @return XML file for project */
  private static Document getFile(final IProject $) {
    try {
      return getFileInner($);
    } catch (final ParserConfigurationException | CoreException | SAXException | IOException ¢) {
      return note.bug(¢);
    }
  }

  /** Return XML file for given project. Creates one if absent.
   * @param p JD
   * @return XML file for project
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   * @throws CoreException
   * @return XML file for project */
  private static Document getFileInner(final IProject p) throws CoreException, ParserConfigurationException, SAXException, IOException {
    if (p == null || !p.exists() || !p.isOpen())
      return null;
    final IFile fl = p.getFile(FILE_NAME);
    if (fl == null)
      return null;
    final DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
    if (df == null)
      return null;
    final DocumentBuilder b = df.newDocumentBuilder();
    if (b == null)
      return null;
    if (!fl.exists()) {
      final Document i = initialize(b.newDocument());
      if (i == null)
        return null;
      fl.create(new ByteArrayInputStream("".getBytes()), true, new NullProgressMonitor());
      if (!commit(fl, i) || !fl.exists())
        return null;
    }
    Document $ = b.parse(fl.getContents());
    if ($ == null)
      return null;
    final Element e = $.getDocumentElement();
    if (e == null)
      return null;
    e.normalize();
    final NodeList ns = $.getElementsByTagName(BASE);
    if (ns != null && ns.getLength() == 1 && validate($, ((Element) ns.item(0)).getAttribute(VERSION)))
      return $;
    $ = initialize(b.newDocument());
    commit(fl, $);
    return $;
  }

  /** Initialize XML document. Enables all tippers, except declared non core
   * tippers, also, add to the document the default values for naming\notations
   * prefrences.
   * @param d JD
   * @return given document */
  private static Document initialize(final Document $) {
    if ($ == null)
      return null;
    if ($.getElementById(BASE) != null)
      return $;
    final Element e = $.createElement("spartan"), t = $.createElement("tippers"), n = $.createElement("notations");
    e.setAttribute(VERSION, CURRENT_VERSION);
    final Collection<String> seen = new HashSet<>();
    Configurations.allTippers().forEach(λ -> createEnabledNodeChild($, λ, seen, t));
    createNotationChild($, "Cent", "¢", seen, n);
    e.appendChild(n);
    createNotationChild($, "Dollar", "$", seen, n);
    e.appendChild(n);
    $.appendChild(e);
    e.appendChild(t);
    $.setXmlStandalone(true); // TODO Roth: does not seem to work
    return $;
  }

  @SuppressWarnings("unchecked") private static Class<Tipper<?>> unchecked(@SuppressWarnings("rawtypes") final Class<? extends Tipper> λ) {
    return (Class<Tipper<?>>) λ;
  }

  /** Manipulates documents with different version / corrupted files.
   * @param $ plugin's XML document
   * @param version document's version
   * @return true iff the document is valid, and does not require
   *         initialization */
  private static boolean validate(@SuppressWarnings("unused") final Document $, final String version) {
    return version != null && Double.parseDouble(version) >= 3.0;
  }

  /** Describes an XML category element for plugin's XML file. The category has
   * a list of its {@link SpartanElement} children.
   * @author Ori Roth {@code ori.rothh@gmail.com}
   * @since 2017-02-25 */
  public static class SpartanCategory extends SpartanElement {
    private final List<SpartanElement> children;

    public SpartanCategory(final String name, final boolean enabled) {
      super(name, enabled);
      children = an.empty.list();
    }

    public void addChild(final SpartanTipper ¢) {
      children.add(¢);
    }

    @Override public SpartanElement[] getChildren() {
      return children.toArray(new SpartanElement[children.size()]);
    }

    @Override public boolean hasChildren() {
      return !children.isEmpty();
    }
  }

  /** Describes an XML element for plugin's XML file.
   * @author Ori Roth {@code ori.rothh@gmail.com}
   * @since 2017-02-25 */
  public abstract static class SpartanElement {
    public static final SpartanElement[] EMPTY = new SpartanElement[0];
    private boolean enabled;
    private final String name;

    public SpartanElement(final String name, final boolean enabled) {
      this.name = name;
      this.enabled = enabled;
    }

    public void enable(final boolean enable) {
      enabled = enable;
    }

    public boolean enabled() {
      return enabled;
    }

    @SuppressWarnings("static-method") public SpartanElement[] getChildren() {
      return EMPTY;
    }

    @SuppressWarnings("static-method") public boolean hasChildren() {
      return false;
    }

    public String name() {
      return name;
    }
  }

  /** Describes an XML tipper element for plugin's XML file. The tipper is
   * connected to {@link SpartanCategory}, and has a description.
   * @author Ori Roth {@code ori.rothh@gmail.com}
   * @since 2017-02-25 */
  public static class SpartanTipper extends SpartanElement {
    final String description;
    final SpartanCategory parent;
    final Examples preview;

    public SpartanTipper(final String name, final boolean enabled, final SpartanCategory parent, final String description, final Examples preview) {
      super(name, enabled);
      this.parent = parent;
      this.description = description;
      this.preview = preview;
    }
  }
}
