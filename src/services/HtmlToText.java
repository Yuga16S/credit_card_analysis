package services;

import java.io.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
import java.net.*;

/**
 * 
 * @author Riteesh
 *
 */
//This class is used by CreateTextFiles
public class HtmlToText extends HTMLEditorKit.ParserCallback {
	StringBuffer s; // StringBuffer declaration

	public HtmlToText() {
	} // empty constructor

	public void parse(Reader in) throws IOException {
		s = new StringBuffer(); // intialization of StringBuffer
		ParserDelegator delegator = new ParserDelegator(); // creating ParserDelegator object
		delegator.parse(in, this, Boolean.TRUE); // the third parameter is set to boolean TRUE to ignore the charset
													// directive
	}

	public void handleText(char[] text, int pos) {
		s.append(text);
	}

	public String getText() {
		return s.toString();
	}
	

}