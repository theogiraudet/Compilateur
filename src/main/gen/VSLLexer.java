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
		LB=10, RB=11, IDENT=12, TEXT=13, INTEGER=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WS", "COMMENT", "LETTER", "DIGIT", "ASCII", "LP", "RP", "PLUS", "MINUS", 
			"TIMES", "DIVIDE", "AFFECT", "LB", "RB", "IDENT", "TEXT", "INTEGER"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'('", "')'", "'+'", "'-'", "'*'", "'/'", "':='", "'{'", 
			"'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "COMMENT", "LP", "RP", "PLUS", "MINUS", "TIMES", "DIVIDE", 
			"AFFECT", "LB", "RB", "IDENT", "TEXT", "INTEGER"
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
		case 15:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20d\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3.\n\3\f\3\16\3\61\13\3\3\3\3\3\3\4"+
		"\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f"+
		"\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\7\20Q\n\20\f\20\16\20"+
		"T\13\20\3\21\3\21\7\21X\n\21\f\21\16\21[\13\21\3\21\3\21\3\21\3\22\6\22"+
		"a\n\22\r\22\16\22b\2\2\23\3\3\5\4\7\2\t\2\13\2\r\5\17\6\21\7\23\b\25\t"+
		"\27\n\31\13\33\f\35\r\37\16!\17#\20\3\2\5\5\2\13\f\17\17\"\"\3\2\f\f\4"+
		"\2\f\f$$\2e\2\3\3\2\2\2\2\5\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5)\3\2\2\2"+
		"\7\64\3\2\2\2\t\66\3\2\2\2\138\3\2\2\2\r:\3\2\2\2\17<\3\2\2\2\21>\3\2"+
		"\2\2\23@\3\2\2\2\25B\3\2\2\2\27D\3\2\2\2\31F\3\2\2\2\33I\3\2\2\2\35K\3"+
		"\2\2\2\37M\3\2\2\2!U\3\2\2\2#`\3\2\2\2%&\t\2\2\2&\'\3\2\2\2\'(\b\2\2\2"+
		"(\4\3\2\2\2)*\7\61\2\2*+\7\61\2\2+/\3\2\2\2,.\n\3\2\2-,\3\2\2\2.\61\3"+
		"\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\62\3\2\2\2\61/\3\2\2\2\62\63\b\3\2\2\63"+
		"\6\3\2\2\2\64\65\4c|\2\65\b\3\2\2\2\66\67\4\62;\2\67\n\3\2\2\289\n\4\2"+
		"\29\f\3\2\2\2:;\7*\2\2;\16\3\2\2\2<=\7+\2\2=\20\3\2\2\2>?\7-\2\2?\22\3"+
		"\2\2\2@A\7/\2\2A\24\3\2\2\2BC\7,\2\2C\26\3\2\2\2DE\7\61\2\2E\30\3\2\2"+
		"\2FG\7<\2\2GH\7?\2\2H\32\3\2\2\2IJ\7}\2\2J\34\3\2\2\2KL\7\177\2\2L\36"+
		"\3\2\2\2MR\5\7\4\2NQ\5\7\4\2OQ\5\t\5\2PN\3\2\2\2PO\3\2\2\2QT\3\2\2\2R"+
		"P\3\2\2\2RS\3\2\2\2S \3\2\2\2TR\3\2\2\2UY\7$\2\2VX\5\13\6\2WV\3\2\2\2"+
		"X[\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z\\\3\2\2\2[Y\3\2\2\2\\]\7$\2\2]^\b\21\3"+
		"\2^\"\3\2\2\2_a\5\t\5\2`_\3\2\2\2ab\3\2\2\2b`\3\2\2\2bc\3\2\2\2c$\3\2"+
		"\2\2\b\2/PRYb\4\b\2\2\3\21\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}