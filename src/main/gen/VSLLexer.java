// Generated from C:/Users/Mech-Robo Neko-Stro/istic/M1/SE/PDS-TP2/src/main/antlr\VSLLexer.g4 by ANTLR 4.8

  package TP2;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VSLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, COMMENT=2, LP=3, RP=4, PLUS=5, MINUS=6, TIMES=7, DIVIDE=8, AFFECT=9, 
		LB=10, RB=11, LSB=12, RSB=13, COMMA=14, IF=15, THEN=16, ELSE=17, FI=18, 
		WHILE=19, DO=20, DONE=21, FUNC=22, PROTO=23, INT=24, VOID=25, IDENT=26, 
		TEXT=27, INTEGER=28;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WS", "COMMENT", "LETTER", "DIGIT", "ASCII", "LP", "RP", "PLUS", "MINUS", 
			"TIMES", "DIVIDE", "AFFECT", "LB", "RB", "LSB", "RSB", "COMMA", "IF", 
			"THEN", "ELSE", "FI", "WHILE", "DO", "DONE", "FUNC", "PROTO", "INT", 
			"VOID", "IDENT", "TEXT", "INTEGER"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'('", "')'", "'+'", "'-'", "'*'", "'/'", "':='", "'{'", 
			"'}'", "'['", "']'", "','", "'IF'", "'THEN'", "'ELSE'", "'FI'", "'WHILE'", 
			"'DO'", "'DONE'", "'FUNC'", "'PROTO'", "'INT'", "'VOID'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "COMMENT", "LP", "RP", "PLUS", "MINUS", "TIMES", "DIVIDE", 
			"AFFECT", "LB", "RB", "LSB", "RSB", "COMMA", "IF", "THEN", "ELSE", "FI", 
			"WHILE", "DO", "DONE", "FUNC", "PROTO", "INT", "VOID", "IDENT", "TEXT", 
			"INTEGER"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public VSLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "VSLLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 29:
			TEXT_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void TEXT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 setText(getText().substring(1, getText().length() - 1)); 
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\36\u00b8\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3J\n\3\f\3\16\3M\13\3\3\3\3\3\3\4"+
		"\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f"+
		"\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3"+
		"\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3"+
		"\26\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3"+
		"\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3"+
		"\34\3\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\7\36\u00a5\n\36\f\36"+
		"\16\36\u00a8\13\36\3\37\3\37\7\37\u00ac\n\37\f\37\16\37\u00af\13\37\3"+
		"\37\3\37\3\37\3 \6 \u00b5\n \r \16 \u00b6\2\2!\3\3\5\4\7\2\t\2\13\2\r"+
		"\5\17\6\21\7\23\b\25\t\27\n\31\13\33\f\35\r\37\16!\17#\20%\21\'\22)\23"+
		"+\24-\25/\26\61\27\63\30\65\31\67\329\33;\34=\35?\36\3\2\5\5\2\13\f\17"+
		"\17\"\"\3\2\f\f\4\2\f\f$$\2\u00b9\2\3\3\2\2\2\2\5\3\2\2\2\2\r\3\2\2\2"+
		"\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2"+
		"\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2"+
		"\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2"+
		"\2\2=\3\2\2\2\2?\3\2\2\2\3A\3\2\2\2\5E\3\2\2\2\7P\3\2\2\2\tR\3\2\2\2\13"+
		"T\3\2\2\2\rV\3\2\2\2\17X\3\2\2\2\21Z\3\2\2\2\23\\\3\2\2\2\25^\3\2\2\2"+
		"\27`\3\2\2\2\31b\3\2\2\2\33e\3\2\2\2\35g\3\2\2\2\37i\3\2\2\2!k\3\2\2\2"+
		"#m\3\2\2\2%o\3\2\2\2\'r\3\2\2\2)w\3\2\2\2+|\3\2\2\2-\177\3\2\2\2/\u0085"+
		"\3\2\2\2\61\u0088\3\2\2\2\63\u008d\3\2\2\2\65\u0092\3\2\2\2\67\u0098\3"+
		"\2\2\29\u009c\3\2\2\2;\u00a1\3\2\2\2=\u00a9\3\2\2\2?\u00b4\3\2\2\2AB\t"+
		"\2\2\2BC\3\2\2\2CD\b\2\2\2D\4\3\2\2\2EF\7\61\2\2FG\7\61\2\2GK\3\2\2\2"+
		"HJ\n\3\2\2IH\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL\3\2\2\2LN\3\2\2\2MK\3\2\2\2"+
		"NO\b\3\2\2O\6\3\2\2\2PQ\4c|\2Q\b\3\2\2\2RS\4\62;\2S\n\3\2\2\2TU\n\4\2"+
		"\2U\f\3\2\2\2VW\7*\2\2W\16\3\2\2\2XY\7+\2\2Y\20\3\2\2\2Z[\7-\2\2[\22\3"+
		"\2\2\2\\]\7/\2\2]\24\3\2\2\2^_\7,\2\2_\26\3\2\2\2`a\7\61\2\2a\30\3\2\2"+
		"\2bc\7<\2\2cd\7?\2\2d\32\3\2\2\2ef\7}\2\2f\34\3\2\2\2gh\7\177\2\2h\36"+
		"\3\2\2\2ij\7]\2\2j \3\2\2\2kl\7_\2\2l\"\3\2\2\2mn\7.\2\2n$\3\2\2\2op\7"+
		"K\2\2pq\7H\2\2q&\3\2\2\2rs\7V\2\2st\7J\2\2tu\7G\2\2uv\7P\2\2v(\3\2\2\2"+
		"wx\7G\2\2xy\7N\2\2yz\7U\2\2z{\7G\2\2{*\3\2\2\2|}\7H\2\2}~\7K\2\2~,\3\2"+
		"\2\2\177\u0080\7Y\2\2\u0080\u0081\7J\2\2\u0081\u0082\7K\2\2\u0082\u0083"+
		"\7N\2\2\u0083\u0084\7G\2\2\u0084.\3\2\2\2\u0085\u0086\7F\2\2\u0086\u0087"+
		"\7Q\2\2\u0087\60\3\2\2\2\u0088\u0089\7F\2\2\u0089\u008a\7Q\2\2\u008a\u008b"+
		"\7P\2\2\u008b\u008c\7G\2\2\u008c\62\3\2\2\2\u008d\u008e\7H\2\2\u008e\u008f"+
		"\7W\2\2\u008f\u0090\7P\2\2\u0090\u0091\7E\2\2\u0091\64\3\2\2\2\u0092\u0093"+
		"\7R\2\2\u0093\u0094\7T\2\2\u0094\u0095\7Q\2\2\u0095\u0096\7V\2\2\u0096"+
		"\u0097\7Q\2\2\u0097\66\3\2\2\2\u0098\u0099\7K\2\2\u0099\u009a\7P\2\2\u009a"+
		"\u009b\7V\2\2\u009b8\3\2\2\2\u009c\u009d\7X\2\2\u009d\u009e\7Q\2\2\u009e"+
		"\u009f\7K\2\2\u009f\u00a0\7F\2\2\u00a0:\3\2\2\2\u00a1\u00a6\5\7\4\2\u00a2"+
		"\u00a5\5\7\4\2\u00a3\u00a5\5\t\5\2\u00a4\u00a2\3\2\2\2\u00a4\u00a3\3\2"+
		"\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"<\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00ad\7$\2\2\u00aa\u00ac\5\13\6\2"+
		"\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae"+
		"\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00b1\7$\2\2\u00b1"+
		"\u00b2\b\37\3\2\u00b2>\3\2\2\2\u00b3\u00b5\5\t\5\2\u00b4\u00b3\3\2\2\2"+
		"\u00b5\u00b6\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7@\3"+
		"\2\2\2\b\2K\u00a4\u00a6\u00ad\u00b6\4\b\2\2\3\37\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}