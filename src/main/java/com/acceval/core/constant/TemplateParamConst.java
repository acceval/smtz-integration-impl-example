package com.acceval.core.constant;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;

public class TemplateParamConst {

	// Please refer https://wkhtmltopdf.org/usage/wkhtmltopdf.txt for more
	// commands

	public static final String COLLATE = "--collate";
	public static final String NO_COLLATE = "--no-collate";
	public static final String COOKIE_JAR = "--cookie-jar";
	public static final String COPIES = "--copies";
	public static final String DPI = "--dpi";
	public static final String GRAY_SCALE = "--grayscale";
	public static final String IMAGE_DPI = "--image-dpi";
	public static final String IMAGE_QUALITY = "--image-quality";
	public static final String LOW_QUALITY = "--lowquality";
	public static final String MARGIN_BOTTOM = "--margin-bottom";
	public static final String MARGIN_LEFT = "--margin-left";
	public static final String MARGIN_RIGHT = "--margin-right";
	public static final String MARGIN_TOP = "--margin-top";
	public static final String ORIENTATION = "--orientation";
	public static final String PAGE_HEIGHT = "--page-height";
	public static final String PAGE_SIZE = "--page-size";
	public static final String PAGE_WIDTH = "--page-width";
	public static final String NO_PDF_COMPRESSION = "--no-pdf-compression";
	public static final String QUITE = "--quiet";
	public static final String TITLE = "--title";
	public static final String MINIMUM_FONT_SIZE = "--minimum-font-size";
	public static final String FOOTER_HTML = "--footer-html";
	public static final String HEADER_HTML = "--header-html";
	public static final String TABLE_OF_CONTENT_XSL = "--xsl-style-sheet";
	public static final String REPLACE = "--replace";

	// below are samples of parameters with/without value needed

	// Collate when printing multiple copies (default)
	public Param collate() {
		return new Param(COLLATE);
	}

	// Do not collate when printing multiple copies
	public Param noCollate() {
		return new Param(NO_COLLATE);
	}

	// Read and write cookies from and to the supplied cookie jar file
	public Param cookieJar(String path) {
		return new Param(COOKIE_JAR, path);
	}

	// Number of copies to print into the pdf file (default 1)
	public Param copies(int copyNum) {
		return new Param(COPIES, String.valueOf(copyNum));
	}

	// Change the dpi explicitly (this has no effect on X11 based systems)
	// (default 96)
	public Param dpi(int dpiNum) {
		return new Param(DPI, String.valueOf(dpiNum));
	}

	// PDF will be generated in grayscale
	public Param grayScale() {
		return new Param(GRAY_SCALE);
	}

	// When embedding images scale them down to this dpi (default 600)
	public Param imageDpi(int dpiNum) {
		return new Param(IMAGE_DPI, String.valueOf(dpiNum));
	}

	// When jpeg compressing images use this quality (default 94)
	public Param imageQuality(int qualityNum) {
		return new Param(IMAGE_QUALITY, String.valueOf(qualityNum));
	}

	// Generates lower quality pdf/ps. Useful to shrink the result document
	// space
	public Param lowQuality() {
		return new Param(LOW_QUALITY);
	}

	// Set the page bottom margin
	public Param marginBottom(String unitValue) {
		return new Param(MARGIN_BOTTOM, unitValue);
	}

	// Set the page left margin (default 10mm)
	public Param marginLeft(String unitValue) {
		return new Param(MARGIN_LEFT, unitValue);
	}

	// Set the page right margin (default 10mm)
	public Param marginRight(String unitValue) {
		return new Param(MARGIN_RIGHT, unitValue);
	}

	// Set the page top margin
	public Param marginTop(String unitValue) {
		return new Param(MARGIN_TOP, unitValue);
	}

	// Set orientation to Landscape or Portrait (default Portrait)
	public Param orientation(String orientation) {
		return new Param(ORIENTATION, orientation);
	}

	// Page height
	public Param pageHeight(String unitValue) {
		return new Param(PAGE_HEIGHT, unitValue);
	}

	// Set paper size to: A4, Letter, etc. (default A4)
	public Param pageSize(String size) {
		return new Param(PAGE_SIZE, size);
	}

	// Page width
	public Param pageWidth(String unitValue) {
		return new Param(PAGE_WIDTH, unitValue);
	}

	// Do not use lossless compression on pdf objects
	public Param noPdfCompression() {
		return new Param(NO_PDF_COMPRESSION);
	}

	// Be less verbose, maintained for backwards compatibility; Same as using
	// --log-level none
	public Param quite() {
		return new Param(QUITE);
	}

	// The title of the generated pdf file (The title of the first document is
	// used if not specified)
	public Param title(String title) {
		return new Param(TITLE, title);
	}

	// Minimum font size
	public Param minimumFontSize(int size) {
		return new Param(MINIMUM_FONT_SIZE, String.valueOf(size));
	}

	// Adds a html header
	public Param headerHTML(String path) {
		return new Param(HEADER_HTML, path);
	}

	// Adds a html footer
	public Param footerHTML(String path) {
		return new Param(FOOTER_HTML, path);
	}

	// Use the supplied xsl style sheet for printing the table of content
	public Param tableOfContentXSL(String path) {
		return new Param(TABLE_OF_CONTENT_XSL);
	}

	public Param replace(String replaceFrom, String replaceTo) {
		return new Param(REPLACE, replaceFrom, replaceTo);
	}
}
