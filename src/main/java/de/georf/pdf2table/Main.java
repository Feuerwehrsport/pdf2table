package de.georf.pdf2table;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class Main {

	public static Logger logger;

	public static void main(String[] args) {
		ArgumentParser parser = ArgumentParsers.newFor("Pdf2Table").build().defaultHelp(true)
				.description("Parses pdf file and creates ods table file");
		parser.addArgument("-d", "--debug").action(Arguments.storeTrue()).help("Enable debug mode");
		parser.addArgument("-v", "--verbose").action(Arguments.storeTrue()).help("Print verbose messages");
		parser.addArgument("-f", "--force").action(Arguments.storeTrue()).help("Force override output files");
		parser.addArgument("-o", "--ods").help("ODS output file (Default: input-file with ods extension)");
		parser.addArgument("file").help("PDF file");
		try {
			Namespace ns = parser.parseArgs(args);
			setLogger(ns.getBoolean("verbose"), ns.getBoolean("debug"));
			String sourcePath = ns.getString("file");
			String outputPath = sourcePath.replaceAll("\\.pdf$", ".ods");
			if (ns.getString("ods") != null)
				outputPath = ns.getString("ods");
			new Pdf2Table(sourcePath, outputPath, ns.getBoolean("force"));
		} catch (ArgumentParserException e) {
			parser.handleError(e);
			System.exit(1);
		} catch (IOException e) {
			logger.fatal(e);
			System.exit(1);
		}
	}

	public static void setLogger(boolean verbose, boolean debug) {
		logger = Logger.getLogger("Pdf2Table");
		logger.setLevel(Level.WARN);
		if (debug)
			logger.setLevel(Level.DEBUG);
		else if (verbose)
			logger.setLevel(Level.INFO);
	}
}
