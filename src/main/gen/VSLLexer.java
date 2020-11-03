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
		WHILE=19, DO=20, DONE=21, INT=22, IDENT=23, TEXT=24, INTEGER=25;
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
			"THEN", "ELSE", "FI", "WHILE", "DO", "DONE", "INT", "IDENT", "TEXT", 
			"INTEGER"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'('", "')'", "'+'", "'-'", "'*'", "'/'", "':='", "'{'", 
			"'}'", "'['", "']'", "','", "'IF'", "'THEN'", "'ELSE'", "'FI'", "'WHILE'", 
			"'DO'", "'DONE'", "'INT'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "COMMENT", "LP", "RP", "PLUS", "MINUS", "TIMES", "DIVIDE", 
			"AFFECT", "LB", "RB", "LSB", "RSB", "COMMA", "IF", "THEN", "ELSE", "FI", 
			"WHILE", "DO", "DONE", "INT", "IDENT", "TEXT", "INTEGER"
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
		case 26:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\33\u00a2\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\7\3D\n\3\f\3\16\3G\13\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\17"+
		"\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\24"+
		"\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33"+
		"\3\33\3\33\7\33\u008f\n\33\f\33\16\33\u0092\13\33\3\34\3\34\7\34\u0096"+
		"\n\34\f\34\16\34\u0099\13\34\3\34\3\34\3\34\3\35\6\35\u009f\n\35\r\35"+
		"\16\35\u00a0\2\2\36\3\3\5\4\7\2\t\2\13\2\r\5\17\6\21\7\23\b\25\t\27\n"+
		"\31\13\33\f\35\r\37\16!\17#\20%\21\'\22)\23+\24-\25/\26\61\27\63\30\65"+
		"\31\67\329\33\3\2\5\5\2\13\f\17\17\"\"\3\2\f\f\4\2\f\f$$\2\u00a3\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3"+
		"\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2"+
		"\67\3\2\2\2\29\3\2\2\2\3;\3\2\2\2\5?\3\2\2\2\7J\3\2\2\2\tL\3\2\2\2\13"+
		"N\3\2\2\2\rP\3\2\2\2\17R\3\2\2\2\21T\3\2\2\2\23V\3\2\2\2\25X\3\2\2\2\27"+
		"Z\3\2\2\2\31\\\3\2\2\2\33_\3\2\2\2\35a\3\2\2\2\37c\3\2\2\2!e\3\2\2\2#"+
		"g\3\2\2\2%i\3\2\2\2\'l\3\2\2\2)q\3\2\2\2+v\3\2\2\2-y\3\2\2\2/\177\3\2"+
		"\2\2\61\u0082\3\2\2\2\63\u0087\3\2\2\2\65\u008b\3\2\2\2\67\u0093\3\2\2"+
		"\29\u009e\3\2\2\2;<\t\2\2\2<=\3\2\2\2=>\b\2\2\2>\4\3\2\2\2?@\7\61\2\2"+
		"@A\7\61\2\2AE\3\2\2\2BD\n\3\2\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2"+
		"\2FH\3\2\2\2GE\3\2\2\2HI\b\3\2\2I\6\3\2\2\2JK\4c|\2K\b\3\2\2\2LM\4\62"+
		";\2M\n\3\2\2\2NO\n\4\2\2O\f\3\2\2\2PQ\7*\2\2Q\16\3\2\2\2RS\7+\2\2S\20"+
		"\3\2\2\2TU\7-\2\2U\22\3\2\2\2VW\7/\2\2W\24\3\2\2\2XY\7,\2\2Y\26\3\2\2"+
		"\2Z[\7\61\2\2[\30\3\2\2\2\\]\7<\2\2]^\7?\2\2^\32\3\2\2\2_`\7}\2\2`\34"+
		"\3\2\2\2ab\7\177\2\2b\36\3\2\2\2cd\7]\2\2d \3\2\2\2ef\7_\2\2f\"\3\2\2"+
		"\2gh\7.\2\2h$\3\2\2\2ij\7K\2\2jk\7H\2\2k&\3\2\2\2lm\7V\2\2mn\7J\2\2no"+
		"\7G\2\2op\7P\2\2p(\3\2\2\2qr\7G\2\2rs\7N\2\2st\7U\2\2tu\7G\2\2u*\3\2\2"+
		"\2vw\7H\2\2wx\7K\2\2x,\3\2\2\2yz\7Y\2\2z{\7J\2\2{|\7K\2\2|}\7N\2\2}~\7"+
		"G\2\2~.\3\2\2\2\177\u0080\7F\2\2\u0080\u0081\7Q\2\2\u0081\60\3\2\2\2\u0082"+
		"\u0083\7F\2\2\u0083\u0084\7Q\2\2\u0084\u0085\7P\2\2\u0085\u0086\7G\2\2"+
		"\u0086\62\3\2\2\2\u0087\u0088\7K\2\2\u0088\u0089\7P\2\2\u0089\u008a\7"+
		"V\2\2\u008a\64\3\2\2\2\u008b\u0090\5\7\4\2\u008c\u008f\5\7\4\2\u008d\u008f"+
		"\5\t\5\2\u008e\u008c\3\2\2\2\u008e\u008d\3\2\2\2\u008f\u0092\3\2\2\2\u0090"+
		"\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\66\3\2\2\2\u0092\u0090\3\2\2"+
		"\2\u0093\u0097\7$\2\2\u0094\u0096\5\13\6\2\u0095\u0094\3\2\2\2\u0096\u0099"+
		"\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u009a\3\2\2\2\u0099"+
		"\u0097\3\2\2\2\u009a\u009b\7$\2\2\u009b\u009c\b\34\3\2\u009c8\3\2\2\2"+
		"\u009d\u009f\5\t\5\2\u009e\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u009e"+
		"\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1:\3\2\2\2\b\2E\u008e\u0090\u0097\u00a0"+
		"\4\b\2\2\3\34\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}