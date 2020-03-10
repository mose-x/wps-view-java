package com.web.wps.util.file;

/**
 * 文件类型枚取
 */
public enum FileType {

	JPEG("FFD8FF"),//JPEG (jpg)

	PNG("89504E47"),//PNG (png)

	GIF("47494638"),//GIF (gif)

	TIFF("49492A00"),//TIFF (tif)

	BMP("424D"),//Windows Bitmap (bmp)

	DWG("41433130"),//CAD (dwg)

	PSD("38425053"),// Adobe Photoshop.

	RTF("7B5C727466"),// Rich Text Format.

	XML("3C3F786D6C"),// XML.

	HTML("68746D6C3E"),//HTML (html)

	CSS("48544D4C207B0D0A0942"),// CSS.

	JS("696B2E71623D696B2E71"),// JS.

	EML("44656C69766572792D646174653A"),// Email [thorough only].

	DBX("CFAD12FEC5FD746F"),// Outlook Express.

	PST("2142444E"),// Outlook (pst).

	XLS_DOC("D0CF11E0"), // MS Word/Excel.

	XLSX_DOCX("504B030414000600080000002100"),// MS Word/Excel.

	WPSUSER("504B03040A0000000000"),// WPS（个人版）文字wps、表格et、演示dps都是一样的

	WPS("D0CF11E0A1B11AE10000"),// WPS（专业版）文字wps、表格et、演示dps都是一样的

	VSD("d0cf11e0a1b11ae10000"),// Visio

	MDB("5374616E64617264204A"),// MS Access.

	TORRENT("6431303A637265617465"),// torrent

	WPD("FF575043"),// WordPerfect.

	EPS("252150532D41646F6265"),// Postscript.

	PDF("255044462D312E"),// Adobe Acrobat.

	QDF("AC9EBD8F"),// Quicken.

	PWL("E3828596"),// Windows Password.

	ZIP("504B03040A000008"),// ZIP Archive.

	RAR("52617221"),// RAR Archive.

	JSP("3C2540207061676520"),// JSP Archive.

	JAVA("7061636B61676520"),// JAVA Archive.

	JAR("504B03040A000000"),// JAR Archive.

	MF("4D616E69666573742D56"),// MF Archive.

	EXE("4D5A9000030000000400"),// EXE Archive.

	CHM("49545346030000006000"),// CHM Archive.

	WAV("57415645"),// Wave.

	AVI("41564920"),// AVI.

	RAM("2E7261FD"),// Real Audio.

	RM("2E524D46"),// Real Media.

	MPG("000001BA"),// MPEG (mpg).

	MOV("6D6F6F76"),// Quicktime.

	ASF("3026B2758E66CF11"),// Windows Media.

	MID("4D546864"),// MIDI.

	MP4("00000020667479706d70"),// MP4.

	MP3("49443303000000002176"),// MP3.

	FLV("464C5601050000000900");// FLV.

	private String value = "";

	/**
	 * Constructor.
	 *
	 * @param type
	 */
	private FileType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
