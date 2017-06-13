/* The following code was generated by JFlex 1.6.1 */
/** A general purpose Java tokenizer,
 * @author Yossi Gil
 * @since 2007/04/02 on 2012/10/10 bug fix to deal with the multiline - comment
 *        construct */
package il.org.spartan.java;

import static il.org.spartan.java.Token.*;

@SuppressWarnings("all")
/** This class is a scanner generated by
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.1 from the specification file
 * <tt>C:/Users/melanyc/Spartanizer/il.org.spartan.spartan/src/il/org/spartan/java/Tokenizer.flex</tt> */
public class RawTokenizer {
  /** This character denotes the end of file */
  public static final int YYEOF = -1;
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;
  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int RESET = 2;
  public static final int SCAN_CODE = 4;
  public static final int SCAN_STRING_LITERAL = 6;
  public static final int SCAN_CHAR_LITERAL = 8;
  public static final int SCAN_LINE_COMMENT = 10;
  public static final int SCAN_DOC_COMMENT = 12;
  public static final int SCAN_BLOCK_COMMENT = 14;
  public static final int BLOCK_EOLN = 16;
  public static final int DOC_EOLN = 18;
  /** ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l at the
   * beginning of a line l is of the form l = 2*k, k a non negative integer */
  private static final int ZZ_LEXSTATE[] = { 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8 };
  /** Translates characters to character classes */
  private static final String ZZ_CMAP_PACKED = "\11\5\1\3\1\1\1\16\1\17\1\2\16\5\4\0\1\3\1\51" + "\1\23\1\0\1\4\1\61\1\55\1\24\1\36\1\37\1\22\1\57"
      + "\1\45\1\15\1\13\1\21\1\6\7\12\2\7\1\54\1\44\1\50" + "\1\46\1\47\1\53\1\25\3\11\1\64\1\14\1\63\5\4\1\62"
      + "\13\4\1\10\2\4\1\42\1\65\1\43\1\60\1\4\1\0\1\34" + "\1\11\1\35\1\64\1\31\1\33\2\4\1\26\2\4\1\62\1\4"
      + "\1\27\3\4\1\32\1\4\1\30\3\4\1\10\2\4\1\40\1\56" + "\1\41\1\52\6\5\1\20\32\5\2\0\4\4\4\0\1\4\2\0"
      + "\1\5\7\0\1\4\4\0\1\4\5\0\27\4\1\0\37\4\1\0" + "\u01ca\4\4\0\14\4\16\0\5\4\7\0\1\4\1\0\1\4\21\0"
      + "\160\5\5\4\1\0\2\4\2\0\4\4\10\0\1\4\1\0\3\4" + "\1\0\1\4\1\0\24\4\1\0\123\4\1\0\213\4\1\0\5\5"
      + "\2\0\236\4\11\0\46\4\2\0\1\4\7\0\47\4\7\0\1\4" + "\1\0\55\5\1\0\1\5\1\0\2\5\1\0\2\5\1\0\1\5" + "\10\0\33\4\5\0\3\4\15\0\5\5\6\0\1\4\4\0\13\5"
      + "\5\0\53\4\37\5\4\0\2\4\1\5\143\4\1\0\1\4\10\5" + "\1\0\6\5\2\4\2\5\1\0\4\5\2\4\12\5\3\4\2\0"
      + "\1\4\17\0\1\5\1\4\1\5\36\4\33\5\2\0\131\4\13\5" + "\1\4\16\0\12\5\41\4\11\5\2\4\4\0\1\4\5\0\26\4"
      + "\4\5\1\4\11\5\1\4\3\5\1\4\5\5\22\0\31\4\3\5" + "\104\0\1\4\1\0\13\4\67\0\33\5\1\0\4\5\66\4\3\5"
      + "\1\4\22\5\1\4\7\5\12\4\2\5\2\0\12\5\1\0\7\4" + "\1\0\7\4\1\0\3\5\1\0\10\4\2\0\2\4\2\0\26\4" + "\1\0\7\4\1\0\1\4\3\0\4\4\2\0\1\5\1\4\7\5"
      + "\2\0\2\5\2\0\3\5\1\4\10\0\1\5\4\0\2\4\1\0" + "\3\4\2\5\2\0\12\5\4\4\7\0\1\4\5\0\3\5\1\0" + "\6\4\4\0\2\4\2\0\26\4\1\0\7\4\1\0\2\4\1\0"
      + "\2\4\1\0\2\4\2\0\1\5\1\0\5\5\4\0\2\5\2\0" + "\3\5\3\0\1\5\7\0\4\4\1\0\1\4\7\0\14\5\3\4" + "\1\5\13\0\3\5\1\0\11\4\1\0\3\4\1\0\26\4\1\0"
      + "\7\4\1\0\2\4\1\0\5\4\2\0\1\5\1\4\10\5\1\0" + "\3\5\1\0\3\5\2\0\1\4\17\0\2\4\2\5\2\0\12\5" + "\1\0\1\4\17\0\3\5\1\0\10\4\2\0\2\4\2\0\26\4"
      + "\1\0\7\4\1\0\2\4\1\0\5\4\2\0\1\5\1\4\7\5" + "\2\0\2\5\2\0\3\5\10\0\2\5\4\0\2\4\1\0\3\4" + "\2\5\2\0\12\5\1\0\1\4\20\0\1\5\1\4\1\0\6\4"
      + "\3\0\3\4\1\0\4\4\3\0\2\4\1\0\1\4\1\0\2\4" + "\3\0\2\4\3\0\3\4\3\0\14\4\4\0\5\5\3\0\3\5" + "\1\0\4\5\2\0\1\4\6\0\1\5\16\0\12\5\11\0\1\4"
      + "\7\0\3\5\1\0\10\4\1\0\3\4\1\0\27\4\1\0\12\4" + "\1\0\5\4\3\0\1\4\7\5\1\0\3\5\1\0\4\5\7\0" + "\2\5\1\0\2\4\6\0\2\4\2\5\2\0\12\5\22\0\2\5"
      + "\1\0\10\4\1\0\3\4\1\0\27\4\1\0\12\4\1\0\5\4" + "\2\0\1\5\1\4\7\5\1\0\3\5\1\0\4\5\7\0\2\5" + "\7\0\1\4\1\0\2\4\2\5\2\0\12\5\1\0\2\4\17\0"
      + "\2\5\1\0\10\4\1\0\3\4\1\0\51\4\2\0\1\4\7\5" + "\1\0\3\5\1\0\4\5\1\4\10\0\1\5\10\0\2\4\2\5" + "\2\0\12\5\12\0\6\4\2\0\2\5\1\0\22\4\3\0\30\4"
      + "\1\0\11\4\1\0\1\4\2\0\7\4\3\0\1\5\4\0\6\5" + "\1\0\1\5\1\0\10\5\22\0\2\5\15\0\60\4\1\5\2\4" + "\7\5\4\0\10\4\10\5\1\0\12\5\47\0\2\4\1\0\1\4"
      + "\2\0\2\4\1\0\1\4\2\0\1\4\6\0\4\4\1\0\7\4" + "\1\0\3\4\1\0\1\4\1\0\1\4\2\0\2\4\1\0\4\4" + "\1\5\2\4\6\5\1\0\2\5\1\4\2\0\5\4\1\0\1\4"
      + "\1\0\6\5\2\0\12\5\2\0\4\4\40\0\1\4\27\0\2\5" + "\6\0\12\5\13\0\1\5\1\0\1\5\1\0\1\5\4\0\2\5" + "\10\4\1\0\44\4\4\0\24\5\1\0\2\5\5\4\13\5\1\0"
      + "\44\5\11\0\1\5\71\0\53\4\24\5\1\4\12\5\6\0\6\4" + "\4\5\4\4\3\5\1\4\3\5\2\4\7\5\3\4\4\5\15\4" + "\14\5\1\4\17\5\2\0\46\4\1\0\1\4\5\0\1\4\2\0"
      + "\53\4\1\0\u014d\4\1\0\4\4\2\0\7\4\1\0\1\4\1\0" + "\4\4\2\0\51\4\1\0\4\4\2\0\41\4\1\0\4\4\2\0" + "\7\4\1\0\1\4\1\0\4\4\2\0\17\4\1\0\71\4\1\0"
      + "\4\4\2\0\103\4\2\0\3\5\40\0\20\4\20\0\125\4\14\0" + "\u026c\4\2\0\21\4\1\0\32\4\5\0\113\4\3\0\3\4\17\0"
      + "\15\4\1\0\4\4\3\5\13\0\22\4\3\5\13\0\22\4\2\5" + "\14\0\15\4\1\0\3\4\1\0\2\5\14\0\64\4\40\5\3\0"
      + "\1\4\3\0\2\4\1\5\2\0\12\5\41\0\3\5\2\0\12\5" + "\6\0\130\4\10\0\51\4\1\5\1\4\5\0\106\4\12\0\35\4"
      + "\3\0\14\5\4\0\14\5\12\0\12\5\36\4\2\0\5\4\13\0" + "\54\4\4\0\21\5\7\4\2\5\6\0\12\5\46\0\27\4\5\5"
      + "\4\0\65\4\12\5\1\0\35\5\2\0\13\5\6\0\12\5\15\0" + "\1\4\130\0\5\5\57\4\21\5\7\4\4\0\12\5\21\0\11\5"
      + "\14\0\3\5\36\4\15\5\2\4\12\5\54\4\16\5\14\0\44\4" + "\24\5\10\0\12\5\3\0\3\4\12\5\44\4\122\0\3\5\1\0"
      + "\25\5\4\4\1\5\4\4\3\5\2\4\11\0\300\4\47\5\25\0" + "\4\5\u0116\4\2\0\6\4\2\0\46\4\2\0\6\4\2\0\10\4"
      + "\1\0\1\4\1\0\1\4\1\0\1\4\1\0\37\4\2\0\65\4" + "\1\0\7\4\1\0\1\4\3\0\3\4\1\0\7\4\3\0\4\4" + "\2\0\6\4\4\0\15\4\5\0\3\4\1\0\7\4\16\0\5\5"
      + "\30\0\1\16\1\16\5\5\20\0\2\4\23\0\1\4\13\0\5\5" + "\5\0\6\5\1\0\1\4\15\0\1\4\20\0\15\4\3\0\33\4"
      + "\25\0\15\5\4\0\1\5\3\0\14\5\21\0\1\4\4\0\1\4" + "\2\0\12\4\1\0\1\4\3\0\5\4\6\0\1\4\1\0\1\4" + "\1\0\1\4\1\0\4\4\1\0\13\4\2\0\4\4\5\0\5\4"
      + "\4\0\1\4\21\0\51\4\u0a77\0\57\4\1\0\57\4\1\0\205\4" + "\6\0\4\4\3\5\2\4\14\0\46\4\1\0\1\4\5\0\1\4"
      + "\2\0\70\4\7\0\1\4\17\0\1\5\27\4\11\0\7\4\1\0" + "\7\4\1\0\7\4\1\0\7\4\1\0\7\4\1\0\7\4\1\0"
      + "\7\4\1\0\7\4\1\0\40\5\57\0\1\4\u01d5\0\3\4\31\0" + "\11\4\6\5\1\0\5\4\2\0\5\4\4\0\126\4\2\0\2\5"
      + "\2\0\3\4\1\0\132\4\1\0\4\4\5\0\51\4\3\0\136\4" + "\21\0\33\4\65\0\20\4\u0200\0\u19b6\4\112\0\u51cd\4\63\0\u048d\4"
      + "\103\0\56\4\2\0\u010d\4\3\0\20\4\12\5\2\4\24\0\57\4" + "\1\5\4\0\12\5\1\0\31\4\7\0\1\5\120\4\2\5\45\0"
      + "\11\4\2\0\147\4\2\0\4\4\1\0\4\4\14\0\13\4\115\0" + "\12\4\1\5\3\4\1\5\4\4\1\5\27\4\5\5\20\0\1\4"
      + "\7\0\64\4\14\0\2\5\62\4\21\5\13\0\12\5\6\0\22\5" + "\6\4\3\0\1\4\4\0\12\5\34\4\10\5\2\0\27\4\15\5"
      + "\14\0\35\4\3\0\4\5\57\4\16\5\16\0\1\4\12\5\46\0" + "\51\4\16\5\11\0\3\4\1\5\10\4\2\5\2\0\12\5\6\0"
      + "\27\4\3\0\1\4\1\5\4\0\60\4\1\5\1\4\3\5\2\4" + "\2\5\5\4\2\5\1\4\1\5\1\4\30\0\3\4\2\0\13\4" + "\5\5\2\0\3\4\2\5\12\0\6\4\2\0\6\4\2\0\6\4"
      + "\11\0\7\4\1\0\7\4\221\0\43\4\10\5\1\0\2\5\2\0" + "\12\5\6\0\u2ba4\4\14\0\27\4\4\0\61\4\u2104\0\u016e\4\2\0"
      + "\152\4\46\0\7\4\14\0\5\4\5\0\1\4\1\5\12\4\1\0" + "\15\4\1\0\5\4\1\0\1\4\1\0\2\4\1\0\2\4\1\0"
      + "\154\4\41\0\u016b\4\22\0\100\4\2\0\66\4\50\0\15\4\3\0" + "\20\5\20\0\7\5\14\0\2\4\30\0\3\4\31\0\1\4\6\0"
      + "\5\4\1\0\207\4\2\0\1\5\4\0\1\4\13\0\12\5\7\0" + "\32\4\4\0\1\4\1\0\32\4\13\0\131\4\3\0\6\4\2\0" + "\6\4\2\0\6\4\2\0\3\4\3\0\2\4\3\0\2\4\22\0"
      + "\3\5\4\0\14\4\1\0\32\4\1\0\23\4\1\0\2\4\1\0" + "\17\4\2\0\16\4\42\0\173\4\105\0\65\4\210\0\1\5\202\0"
      + "\35\4\3\0\61\4\57\0\37\4\21\0\33\4\65\0\36\4\2\0" + "\44\4\4\0\10\4\1\0\5\4\52\0\236\4\2\0\12\5\u0356\0"
      + "\6\4\2\0\1\4\1\0\54\4\1\0\2\4\3\0\1\4\2\0" + "\27\4\252\0\26\4\12\0\32\4\106\0\70\4\6\0\2\4\100\0"
      + "\1\4\3\5\1\0\2\5\5\0\4\5\4\4\1\0\3\4\1\0" + "\33\4\4\0\3\5\4\0\1\5\40\0\35\4\203\0\66\4\12\0"
      + "\26\4\12\0\23\4\215\0\111\4\u03b7\0\3\5\65\4\17\5\37\0" + "\12\5\20\0\3\5\55\4\13\5\2\0\1\5\22\0\31\4\7\0"
      + "\12\5\6\0\3\5\44\4\16\5\1\0\12\5\100\0\3\5\60\4" + "\16\5\4\4\13\0\12\5\u04a6\0\53\4\15\5\10\0\12\5\u0936\0"
      + "\u036f\4\221\0\143\4\u0b9d\0\u042f\4\u33d1\0\u0239\4\u04c7\0\105\4\13\0" + "\1\4\56\5\20\0\4\5\15\4\u4060\0\2\4\u2163\0\5\5\3\0"
      + "\26\5\2\0\7\5\36\0\4\5\224\0\3\5\u01bb\0\125\4\1\0" + "\107\4\1\0\2\4\2\0\1\4\2\0\2\4\2\0\4\4\1\0"
      + "\14\4\1\0\1\4\1\0\7\4\1\0\101\4\1\0\4\4\2\0" + "\10\4\1\0\7\4\1\0\34\4\1\0\4\4\1\0\5\4\1\0"
      + "\1\4\3\0\7\4\1\0\u0154\4\2\0\31\4\1\0\31\4\1\0" + "\37\4\1\0\31\4\1\0\37\4\1\0\31\4\1\0\37\4\1\0"
      + "\31\4\1\0\37\4\1\0\31\4\1\0\10\4\2\0\62\5\u1600\0" + "\4\4\1\0\33\4\1\0\2\4\1\0\1\4\2\0\1\4\1\0"
      + "\12\4\1\0\4\4\1\0\1\4\1\0\1\4\6\0\1\4\4\0" + "\1\4\1\0\1\4\1\0\1\4\1\0\3\4\1\0\2\4\1\0" + "\1\4\2\0\1\4\1\0\1\4\1\0\1\4\1\0\1\4\1\0"
      + "\1\4\1\0\2\4\1\0\1\4\2\0\4\4\1\0\7\4\1\0" + "\4\4\1\0\4\4\1\0\1\4\1\0\12\4\1\0\21\4\5\0"
      + "\3\4\1\0\5\4\1\0\21\4\u1144\0\ua6d7\4\51\0\u1035\4\13\0"
      + "\336\4\u3fe2\0\u021e\4\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\u05ee\0"
      + "\1\5\36\0\140\5\200\0\360\5\uffff\0\uffff\0\ufe12\0";
  /** Translates characters to character classes */
  private static final char[] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);
  /** Translates DFA states to action switch labels. */
  private static final int[] ZZ_ACTION = zzUnpackAction();
  private static final String ZZ_ACTION_PACKED_0 = "\11\0\2\1\1\2\2\3\1\4\1\5\2\6\1\7" + "\1\10\1\11\1\12\1\13\1\14\1\2\1\15\1\16"
      + "\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26" + "\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36" + "\1\37\1\40\1\41\2\42\1\43\1\41\2\44\1\45"
      + "\1\41\2\46\2\47\1\41\2\50\1\41\2\51\2\52" + "\1\6\2\0\1\53\1\0\1\54\1\6\1\53\1\55" + "\1\56\1\57\1\60\1\61\1\62\2\63\1\64\1\65"
      + "\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75" + "\1\76\1\77\1\100\1\101\1\102\1\103\1\6\1\53" + "\1\0\1\104\1\63\1\105\1\106\1\107\1\110\1\63"
      + "\1\111\5\63\1\112";

  private static int[] zzUnpackAction() {
    final int[] result = new int[115];
    zzUnpackAction(ZZ_ACTION_PACKED_0, 0, result);
    return result;
  }
  private static int zzUnpackAction(final String packed, final int offset, final int[] result) {
    int i = 0, j = offset;
    for (final int l = packed.length(); i < l;) {
      int count = packed.charAt(i++);
      final int value = packed.charAt(i++);
      do
        result[j++] = value;
      while (--count > 0);
    }
    return j;
  }

  /** Translates a state to a row index in the transition table */
  private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
  private static final String ZZ_ROWMAP_PACKED_0 = "\0\0\0\66\0\154\0\242\0\330\0\u010e\0\u0144\0\u017a"
      + "\0\u01b0\0\u01e6\0\u021c\0\u01e6\0\u01e6\0\u0252\0\u01e6\0\u0288" + "\0\u02be\0\u02f4\0\u032a\0\u0360\0\u0396\0\u03cc\0\u01e6\0\u01e6"
      + "\0\u0402\0\u01e6\0\u01e6\0\u01e6\0\u01e6\0\u01e6\0\u01e6\0\u01e6" + "\0\u01e6\0\u0438\0\u046e\0\u04a4\0\u04da\0\u01e6\0\u01e6\0\u01e6"
      + "\0\u0510\0\u0546\0\u057c\0\u05b2\0\u05e8\0\u01e6\0\u01e6\0\u061e" + "\0\u01e6\0\u0654\0\u01e6\0\u068a\0\u01e6\0\u06c0\0\u01e6\0\u06f6"
      + "\0\u01e6\0\u072c\0\u0762\0\u01e6\0\u0798\0\u07ce\0\u01e6\0\u0804" + "\0\u01e6\0\u083a\0\u0870\0\u08a6\0\u08dc\0\u0912\0\u0948\0\u01e6"
      + "\0\u01e6\0\u01e6\0\u01e6\0\u01e6\0\u01e6\0\u097e\0\u01e6\0\u01e6" + "\0\u09b4\0\u09ea\0\u01e6\0\u01e6\0\u0a20\0\u01e6\0\u0a56\0\u01e6"
      + "\0\u01e6\0\u01e6\0\u01e6\0\u01e6\0\u01e6\0\u01e6\0\u01e6\0\u01e6" + "\0\u01e6\0\u01e6\0\u0a8c\0\u0ac2\0\u0af8\0\u0b2e\0\u0b64\0\u01e6"
      + "\0\u0b9a\0\u01e6\0\u01e6\0\u0bd0\0\u01e6\0\u0c06\0\u0c3c\0\u0c72" + "\0\u0ca8\0\u0cde\0\u09b4";

  private static int[] zzUnpackRowMap() {
    final int[] result = new int[115];
    zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, 0, result);
    return result;
  }
  private static int zzUnpackRowMap(final String packed, final int offset, final int[] result) {
    int i = 0, j = offset;
    for (final int l = packed.length(); i < l;)
      result[j++] = packed.charAt(i++) | packed.charAt(i++) << 16;
    return j;
  }

  /** The transition table of the DFA */
  private static final int[] ZZ_TRANS = zzUnpackTrans();
  private static final String ZZ_TRANS_PACKED_0 = "\2\12\1\13\13\12\3\0\45\12\1\14\1\15\1\16" + "\1\17\1\20\1\14\1\21\1\22\2\20\1\22\1\23"
      + "\1\20\1\24\1\0\1\17\1\0\1\25\1\26\1\27" + "\1\30\1\31\10\20\1\32\1\33\1\34\1\35\1\36" + "\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46"
      + "\1\47\1\50\1\51\1\52\1\53\1\54\1\55\3\20" + "\1\14\1\56\1\57\1\60\13\56\3\0\2\56\1\61" + "\41\56\1\62\1\56\1\63\1\64\13\56\3\0\3\56"
      + "\1\65\40\56\1\66\1\56\1\67\1\70\13\56\3\0" + "\46\56\1\71\1\72\13\56\3\0\1\56\1\73\44\56" + "\1\74\1\75\13\56\3\0\1\56\1\76\43\56\1\0"
      + "\1\77\1\100\64\0\1\101\1\102\152\0\1\12\65\0" + "\1\15\70\0\7\20\1\0\1\20\3\0\1\20\5\0" + "\10\20\24\0\3\20\7\0\1\103\1\104\1\105\1\0"
      + "\1\103\1\106\1\107\14\0\1\107\1\0\1\110\26\0" + "\1\111\1\110\1\112\7\0\2\22\2\0\1\22\1\106" + "\1\107\14\0\1\107\1\0\1\110\26\0\1\111\1\110"
      + "\1\112\7\0\2\106\2\0\1\106\70\0\1\113\30\0" + "\1\114\40\0\1\115\1\116\23\0\1\117\65\0\1\120" + "\23\0\1\121\3\0\2\121\2\0\1\121\11\0\1\122"
      + "\7\121\24\0\3\121\47\0\1\123\65\0\1\124\1\125" + "\64\0\1\126\1\0\1\127\63\0\1\130\65\0\1\131" + "\6\0\1\132\56\0\1\133\7\0\1\134\55\0\1\135"
      + "\10\0\1\136\54\0\1\137\65\0\1\140\20\0\1\57" + "\107\0\1\56\41\0\1\56\1\0\1\63\110\0\1\56" + "\40\0\1\56\1\0\1\67\65\0\1\71\105\0\1\141"
      + "\45\0\1\74\105\0\1\142\45\0\1\77\65\0\1\101" + "\72\0\1\103\1\104\2\0\1\103\1\106\46\0\1\111" + "\11\0\2\104\2\0\1\104\1\106\60\0\2\143\1\0"
      + "\2\143\1\0\1\143\14\0\1\143\1\0\3\143\25\0" + "\2\143\7\0\2\106\2\0\1\106\1\0\1\107\14\0" + "\1\107\1\0\1\110\27\0\1\110\1\112\7\0\2\144"
      + "\2\0\1\144\2\0\1\145\41\0\1\145\30\0\1\146" + "\47\0\7\121\1\0\1\121\3\0\1\121\5\0\10\121" + "\24\0\3\121\5\0\7\121\1\0\1\121\3\0\1\121"
      + "\5\0\1\121\1\147\6\121\24\0\3\121\47\0\1\150" + "\1\151\64\0\1\152\25\0\2\143\1\0\2\143\1\0" + "\1\143\14\0\1\143\1\0\3\143\24\0\1\111\2\143"
      + "\7\0\2\144\2\0\1\144\20\0\1\110\27\0\1\110" + "\1\112\7\0\2\144\2\0\1\144\74\0\1\153\50\0" + "\7\121\1\0\1\121\3\0\1\121\5\0\2\121\1\154"
      + "\5\121\24\0\3\121\47\0\1\155\23\0\7\121\1\0" + "\1\121\3\0\1\121\5\0\3\121\1\156\4\121\24\0" + "\3\121\5\0\7\121\1\0\1\121\3\0\1\121\5\0"
      + "\4\121\1\157\3\121\24\0\3\121\5\0\7\121\1\0" + "\1\121\3\0\1\121\5\0\5\121\1\160\2\121\24\0" + "\3\121\5\0\7\121\1\0\1\121\3\0\1\121\5\0"
      + "\6\121\1\161\1\121\24\0\3\121\5\0\7\121\1\0" + "\1\121\3\0\1\121\5\0\7\121\1\162\24\0\3\121" + "\5\0\7\121\1\0\1\121\3\0\1\121\5\0\3\121"
      + "\1\163\4\121\24\0\3\121\1\0";

  private static int[] zzUnpackTrans() {
    final int[] result = new int[3348];
    zzUnpackTrans(ZZ_TRANS_PACKED_0, 0, result);
    return result;
  }
  private static int zzUnpackTrans(final String packed, final int offset, final int[] result) {
    int i = 0, j = offset;
    for (final int l = packed.length(); i < l;) {
      int count = packed.charAt(i++);
      final int value = packed.charAt(i++) - 1;
      do
        result[j++] = value;
      while (--count > 0);
    }
    return j;
  }

  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = { "Unknown internal scanner error", "Error: could not match input",
      "Error: pushback value was too large" };
  /** ZZ_ATTRIBUTE[aState] contains the attributes of state
   * <code>aState</code> */
  private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();
  private static final String ZZ_ATTRIBUTE_PACKED_0 = "\11\0\1\11\1\1\2\11\1\1\1\11\7\1\2\11" + "\1\1\10\11\4\1\3\11\5\1\2\11\1\1\1\11"
      + "\1\1\1\11\1\1\1\11\1\1\1\11\1\1\1\11" + "\2\1\1\11\2\1\1\11\1\1\1\11\2\1\2\0" + "\1\1\1\0\6\11\1\1\2\11\2\1\2\11\1\1"
      + "\1\11\1\1\13\11\2\1\1\0\2\1\1\11\1\1" + "\2\11\1\1\1\11\6\1";

  private static int[] zzUnpackAttribute() {
    final int[] result = new int[115];
    zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, 0, result);
    return result;
  }
  private static int zzUnpackAttribute(final String packed, final int offset, final int[] result) {
    int i = 0, j = offset;
    for (final int l = packed.length(); i < l;) {
      int count = packed.charAt(i++);
      final int value = packed.charAt(i++);
      do
        result[j++] = value;
      while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;
  /** the current state of the DFA */
  private int zzState;
  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;
  /** this buffer contains the current text to be matched and is the source of
   * the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];
  /** the textposition at the last accepting state */
  private int zzMarkedPos;
  /** the current text position in the buffer */
  private int zzCurrentPos;
  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;
  /** endRead marks the last character in the buffer, that has been read from
   * input */
  private int zzEndRead;
  /** number of newlines encountered up to the start of the matched text */
  private int yyline;
  /** the number of characters up to the start of the matched text */
  private int yychar;
  /** the number of characters from the last newline up to the start of the
   * matched text */
  private int yycolumn;
  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;
  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  /** The number of occupied positions in zzBuffer beyond zzEndRead. When a
   * lead/high surrogate has been read from the input stream into the final
   * zzBuffer position, this will have a value of 1; otherwise, it will have a
   * value of 0. */
  private int zzFinalHighSurrogate;

  /* user code: */
  public String text() {
    return $.length() > 0 ? $ + "" : yytext();
  }
  public void error(final String ¢) {
    System.err.println(notify(¢));
    reset();
  }
  public String notify(final String ¢) {
    return location() + ¢ + " " + token();
  }
  public String token() {
    return "<" + text() + ">";
  }
  public String location() {
    return "[" + line() + "," + column() + "]: ";
  }
  public void reset() {
    truncate();
    yybegin(SCAN_CODE);
  }
  public int line() {
    return yyline + 1;
  }
  public int column() {
    return yycolumn + 1;
  }
  public int chars() {
    return yychar + 1;
  }
  private void endIncluding() {
    extend();
    end();
  }
  private void endExcluding() {
    regret();
    end();
  }
  private void gotoExcluding(final int state) {
    regret();
    goTo(state);
  }
  private void extend() {
    $.append(yytext());
  }
  private void regret() {
    yypushback(yylength());
  }
  private void end() {
    goTo(RESET);
  }
  private void goTo(final int state) {
    yybegin(state);
  }
  private void begin(final int state) {
    truncate();
    extend();
    yybegin(state);
  }
  private void truncate() {
    $.setLength(0);
  }

  private final StringBuffer $ = new StringBuffer();

  /** Creates a new scanner
   * @param in the java.io.Reader to read input from. */
  public RawTokenizer(final java.io.Reader in) {
    reset();
    zzReader = in;
  }
  /** Unpacks the compressed character translation table.
   * @param packed the packed character translation table
   * @return the unpacked character translation table */
  private static char[] zzUnpackCMap(final String packed) {
    final char[] map = new char[0x110000];
    for (int i = 0, j = 0; i < 2866;) {
      int count = packed.charAt(i++);
      final char value = packed.charAt(i++);
      do
        map[j++] = value;
      while (--count > 0);
    }
    return map;
  }
  /** Refills the input buffer.
   * @return <code>false</code>, iff there was new input.
   * @exception java.io.IOException if any I/O-Error occurs */
  private boolean zzRefill() throws java.io.IOException {
    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead, zzBuffer, 0, zzEndRead - zzStartRead);
      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }
    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      final char newBuffer[] = new char[2 * zzBuffer.length];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }
    final int requested = zzBuffer.length - zzEndRead, numRead = zzReader.read(zzBuffer, zzEndRead, requested);
    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0)
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    if (numRead <= 0)
      return true;
    zzEndRead += numRead;
    if (numRead != requested || !Character.isHighSurrogate(zzBuffer[zzEndRead - 1]))
      return false;
    --zzEndRead;
    zzFinalHighSurrogate = 1;
    return false;
  }
  /** Closes the input stream. */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; /* indicate end of file */
    zzEndRead = zzStartRead; /* invalidate buffer */
    if (zzReader != null)
      zzReader.close();
  }
  /** Resets the scanner to read from a new input stream. Does not close the old
   * reader. All internal variables are reset, the old input stream
   * <b>cannot</b> be reused (internal buffer is discarded and lost). Lexical
   * state is set to <tt>ZZ_INITIAL</tt>. Internal scan buffer is resized down
   * to its initial length, if it has grown.
   * @param ¢ the new input stream */
  public final void yyreset(final java.io.Reader ¢) {
    zzReader = ¢;
    zzEOFDone = zzAtEOF = false;
    yyline = yychar = yycolumn = zzFinalHighSurrogate = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }
  /** Returns the current lexical state. */
  public final int yystate() {
    return zzLexicalState;
  }
  /** Enters a new lexical state
   * @param newState the new lexical state */
  public final void yybegin(final int newState) {
    zzLexicalState = newState;
  }
  /** Returns the text matched by the current regular expression. */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
  }
  /** Returns the character at position <tt>pos</tt> from the matched text. It
   * is equivalent to yytext().charAt(pos), but faster
   * @param pos the position of the character to fetch. A value from 0 to
   *        yylength()-1.
   * @return the character at position pos */
  public final char yycharat(final int pos) {
    return zzBuffer[pos + zzStartRead];
  }
  /** Returns the length of the matched text region. */
  public final int yylength() {
    return zzMarkedPos - zzStartRead;
  }
  /** Reports an error that occured while scanning. In a wellformed scanner (no
   * or only correct usage of yypushback(int) and a match-all fallback rule)
   * this method will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong (e.g. a JFlex bug
   * producing a faulty scanner etc.). Usual syntax/scanner level error handling
   * should be done in error fallback rules.
   * @param errorCode the code of the errormessage to display */
  private void zzScanError(final int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (final ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }
    throw new Error(message);
  }
  /** Pushes the specified amount of characters back into the input stream. They
   * will be read again by then next call of the scanning method
   * @param number the number of characters to be read again. This number must
   *        not be greater than yylength()! */
  public void yypushback(final int number) {
    if (number > yylength())
      zzScanError(ZZ_PUSHBACK_2BIG);
    zzMarkedPos -= number;
  }
  /** Resumes scanning until the next regular expression is matched, the end of
   * input is encountered or an I/O-Error occurs.
   * @return the next token
   * @exception java.io.IOException if any I/O-Error occurs */
  public Token next() throws java.io.IOException {
    int zzInput = 0, zzAction, zzCurrentPosL, zzMarkedPosL, zzEndReadL = zzEndRead;
    for (char[] zzBufferL = zzBuffer, zzCMapL = ZZ_CMAP;;) {
      zzMarkedPosL = zzMarkedPos;
      yychar += zzMarkedPosL - zzStartRead;
      boolean zzR = false;
      int zzCh, zzCharCount;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL; zzCurrentPosL += zzCharCount) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
          case '\u000B':
          case '\u000C':
          case '\u0085':
          case '\u2028':
          case '\u2029':
            ++yyline;
            yycolumn = 0;
            zzR = false;
            break;
          case '\r':
            ++yyline;
            yycolumn = 0;
            zzR = true;
            break;
          case '\n':
            if (zzR)
              zzR = false;
            else {
              ++yyline;
              yycolumn = 0;
            }
            break;
          default:
            zzR = false;
            yycolumn += zzCharCount;
        }
      }
      if (zzR) {
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          final boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          zzPeek = !eof && zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek)
          --yyline;
      }
      zzAction = -1;
      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
      zzState = ZZ_LEXSTATE[zzLexicalState];
      int zzAttributes = ZZ_ATTRIBUTE[zzState];
      if ((zzAttributes & 1) == 1)
        zzAction = zzState;
      zzForAction: while (true) {
        if (zzCurrentPosL >= zzEndReadL) {
          if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          zzCurrentPos = zzCurrentPosL;
          zzMarkedPos = zzMarkedPosL;
          final boolean eof = zzRefill();
          zzCurrentPosL = zzCurrentPos;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          zzEndReadL = zzEndRead;
          if (eof) {
            zzInput = YYEOF;
            break zzForAction;
          }
        }
        zzCurrentPosL += Character.charCount(zzInput);
        zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
        final int zzNext = ZZ_TRANS[zzCMapL[zzInput] + ZZ_ROWMAP[zzState]];
        if (zzNext == -1)
          break zzForAction;
        zzState = zzNext;
        zzAttributes = ZZ_ATTRIBUTE[zzState];
        if ((zzAttributes & 1) == 1) {
          zzAction = zzState;
          zzMarkedPosL = zzCurrentPosL;
          if ((zzAttributes & 8) == 8)
            break zzForAction;
        }
      }
      zzMarkedPos = zzMarkedPosL;
      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        switch (zzLexicalState) {
          case YYINITIAL:
            return EOF;
          case 116:
            break;
          case RESET:
            return EOF;
          case 117:
            break;
          case SCAN_CODE:
            return EOF;
          case 118:
            break;
          case SCAN_STRING_LITERAL: {
            regret();
            end();
            return UNTERMINATED_STRING_LITERAL;
          }
          case 119:
            break;
          case SCAN_CHAR_LITERAL: {
            endExcluding();
            return UNTERMINATED_CHARACTER_LITERAL;
          }
          case 120:
            break;
          case SCAN_LINE_COMMENT: {
            endExcluding();
            return LINE_COMMENT;
          }
          case 121:
            break;
          case SCAN_DOC_COMMENT: {
            endExcluding();
            return UNTERMINATED_DOC_COMMENT;
          }
          case 122:
            break;
          case SCAN_BLOCK_COMMENT: {
            endExcluding();
            return UNTERMINATED_BLOCK_COMMENT;
          }
          case 123:
            break;
          default:
            return null;
        }
      } else
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: {
            regret();
            reset();
            continue;
          }
          case 75:
            break;
          case 2:
            return UNKNOWN_CHARACTER;
          case 76:
            break;
          case 3:
            return NL;
          case 77:
            break;
          case 4:
            return SPACE;
          case 78:
            break;
          case 5:
            try {
              return Token.valueOf("_" + yytext());
            } catch (final IllegalArgumentException e) {
              return IDENTIFIER;
            }
          case 79:
            break;
          case 6:
            return INTEGER_LITERAL;
          case 80:
            break;
          case 7:
            return DOT;
          case 81:
            break;
          case 8:
            return MINUS;
          case 82:
            break;
          case 9:
            return DIV;
          case 83:
            break;
          case 10:
            return MULT;
          case 84:
            break;
          case 11: {
            begin(SCAN_STRING_LITERAL);
            continue;
          }
          case 85:
            break;
          case 12: {
            begin(SCAN_CHAR_LITERAL);
            continue;
          }
          case 86:
            break;
          case 13:
            return LPAREN;
          case 87:
            break;
          case 14:
            return RPAREN;
          case 88:
            break;
          case 15:
            return LBRACE;
          case 89:
            break;
          case 16:
            return RBRACE;
          case 90:
            break;
          case 17:
            return LBRACK;
          case 91:
            break;
          case 18:
            return RBRACK;
          case 92:
            break;
          case 19:
            return SEMICOLON;
          case 93:
            break;
          case 20:
            return COMMA;
          case 94:
            break;
          case 21:
            return EQ;
          case 95:
            break;
          case 22:
            return GT;
          case 96:
            break;
          case 23:
            return LT;
          case 97:
            break;
          case 24:
            return NOT;
          case 98:
            break;
          case 25:
            return COMP;
          case 99:
            break;
          case 26:
            return QUESTION;
          case 100:
            break;
          case 27:
            return COLON;
          case 101:
            break;
          case 28:
            return AND;
          case 102:
            break;
          case 29:
            return OR;
          case 103:
            break;
          case 30:
            return PLUS;
          case 104:
            break;
          case 31:
            return XOR;
          case 105:
            break;
          case 32:
            return MOD;
          case 106:
            break;
          case 33: {
            extend();
            continue;
          }
          case 107:
            break;
          case 34: {
            endExcluding();
            return UNTERMINATED_STRING_LITERAL;
          }
          case 108:
            break;
          case 35: {
            endIncluding();
            return STRING_LITERAL;
          }
          case 109:
            break;
          case 36: {
            endExcluding();
            return UNTERMINATED_CHARACTER_LITERAL;
          }
          case 110:
            break;
          case 37: {
            endIncluding();
            return CHARACTER_LITERAL;
          }
          case 111:
            break;
          case 38: {
            endExcluding();
            return LINE_COMMENT;
          }
          case 112:
            break;
          case 39: {
            gotoExcluding(DOC_EOLN);
            return PARTIAL_DOC_COMMENT;
          }
          case 113:
            break;
          case 40: {
            gotoExcluding(BLOCK_EOLN);
            return PARTIAL_BLOCK_COMMENT;
          }
          case 114:
            break;
          case 41: {
            truncate();
            goTo(SCAN_BLOCK_COMMENT);
            return NL_BLOCK_COMMENT;
          }
          case 115:
            break;
          case 42: {
            truncate();
            goTo(SCAN_DOC_COMMENT);
            return NL_DOC_COMMENT;
          }
          case 116:
            break;
          case 43:
            return DOUBLE_LITERAL;
          case 117:
            break;
          case 44:
            return FLOAT_LITERAL;
          case 118:
            break;
          case 45:
            return MINUSMINUS;
          case 119:
            break;
          case 46:
            return MINUSEQ;
          case 120:
            break;
          case 47: {
            begin(SCAN_LINE_COMMENT);
            continue;
          }
          case 121:
            break;
          case 48: {
            begin(SCAN_BLOCK_COMMENT);
            continue;
          }
          case 122:
            break;
          case 49:
            return DIVEQ;
          case 123:
            break;
          case 50:
            return MULTEQ;
          case 124:
            break;
          case 51:
            return ANNOTATION;
          case 125:
            break;
          case 52:
            return EQEQ;
          case 126:
            break;
          case 53:
            return GTEQ;
          case 127:
            break;
          case 54:
            return RSHIFT;
          case 128:
            break;
          case 55:
            return LTEQ;
          case 129:
            break;
          case 56:
            return LSHIFT;
          case 130:
            break;
          case 57:
            return NOTEQ;
          case 131:
            break;
          case 58:
            return ANDEQ;
          case 132:
            break;
          case 59:
            return ANDAND;
          case 133:
            break;
          case 60:
            return OREQ;
          case 134:
            break;
          case 61:
            return OROR;
          case 135:
            break;
          case 62:
            return PLUSEQ;
          case 136:
            break;
          case 63:
            return PLUSPLUS;
          case 137:
            break;
          case 64:
            return XOREQ;
          case 138:
            break;
          case 65:
            return MODEQ;
          case 139:
            break;
          case 66: {
            endIncluding();
            return DOC_COMMENT;
          }
          case 140:
            break;
          case 67: {
            endIncluding();
            return BLOCK_COMMENT;
          }
          case 141:
            break;
          case 68: {
            begin(SCAN_DOC_COMMENT);
            continue;
          }
          case 142:
            break;
          case 69:
            return RSHIFTEQ;
          case 143:
            break;
          case 70:
            return URSHIFT;
          case 144:
            break;
          case 71:
            return LSHIFTEQ;
          case 145:
            break;
          case 72:
            return EMPTY_BLOCK_COMMENT;
          case 146:
            break;
          case 73:
            return URSHIFTEQ;
          case 147:
            break;
          case 74:
            return AT_INTERFACE;
          case 148:
            break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
    }
  }
}
