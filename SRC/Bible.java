import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.io.*;
class Bible extends JFrame implements ItemListener {
	// frame
	static JFrame f;
	// label
	static JLabel l, l1, l2;
	// combobox
	static JComboBox c1;
	static JComboBox c2;
	// textbox
	static JComboBox c3;

	static JTextArea textArea;
	// main class
	public static void main(String[] args)
	{
		// create a new frame
		f = new JFrame("Bible");
		// create a object
		Bible s = new Bible();
		// set layout of frame
		f.getContentPane().setLayout(new FlowLayout());
		// array of string containing cities
		String s1[] = {"Genesis","Exodus","Leviticus","Numbers","Deuteronomy","Joshua","Judges","Ruth","1 Samuel","2 Samuel","1 Kings","2 Kings","1 Chronicles","2 Chronicles","Ezra","Nehemiah","Esther","Job","Psalms","Proverbs","Ecclesiastes","SongofSolomon","Isaiah","Jeremiah","Lamentations","Ezekiel","Daniel","Hosea","Joel","Amos","Obadiah","Jonah","Micah","Nahum","Habakkuk","Zephaniah","Haggai","Zechariah","Malachi", "Matthew","Mark","Luke","John","Acts","Romans","1 Corinthians","2 Corinthians","Galatians","Ephesians","Philippians","Colossians","1 Thessalonians","2 Thessalonians","1 Timothy","2 Timothy","Titus","Philemon","Hebrews","James","1 Peter","2 Peter","1 John","2 John","3 John","Jude","Revelation"};
		// create checkbox
		c1 = new JComboBox(s1);
		// add ItemListener
		c1.addItemListener(s);
		// create labels
		l = new JLabel("Book");
		l1 = new JLabel("Chapter");
		// create checkbox
		String s2[] = {" "};
		c2 = new JComboBox(s2);
		c2.setSelectedItem(null);
		// add ItemListener
		c2.addItemListener(s);
		// create a new panel
		p.setLayout(new GridLayout(3,2,0,3));
		p.add(l);
		// add combobox to panel
		p.add(c1);
		p.add(l1);
		p.add(c2);
		// add panel to frame
		f.getContentPane().add(p,BorderLayout.WEST);
		// text
		textArea = new JTextArea(28, 60);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		f.getContentPane().add(scrollPane, BorderLayout.CENTER);
		textArea.setText(ReadFileToString("Genesis.txt"));
		textArea.setCaretPosition(0);
		// frame code
		f.pack();
		// set the size of frame
		f.setSize(800, 600);
		f.show();
	}

	public static int countOccurences(String text, String word) {
		int occurences = 0;
		int lastIndex = text.indexOf(word);
		while (lastIndex != -1) {
			occurences++;
			lastIndex = text.indexOf(word, lastIndex + word.length());
		}
		return occurences;
	}
	static JPanel p = new JPanel();
	public void itemStateChanged(ItemEvent e) {
		// if the state combobox is changed
		if (e.getSource() == c1) {
			textArea.setText(ReadFileToString(c1.getSelectedItem() + ".txt"));
			textArea.setCaretPosition(0);
			String BibleBook = ReadFileToString(c1.getSelectedItem() + ".txt");
			int ChapterNumbers = countOccurences(BibleBook, "CHAPTER");
			ArrayList<String> Chapters = new ArrayList(ChapterNumbers);
			p.remove(3);
			for (int i = 0; i < ChapterNumbers; i++) {
				Chapters.add(((Integer) (i + 1)).toString());
			}
			c2 = new JComboBox(Chapters.toArray());
			c2.addItemListener(this);
			p.add(c2, 3);
			p.validate();
			p.repaint();
		}
		if (e.getSource() == c2) {
			String BibleBook = ReadFileToString(c1.getSelectedItem() + ".txt");
			int index = 0;
			int ChapterNum = c2.getSelectedIndex() + 1;
			for (int i = 0; i < ChapterNum; i++) {
				index = BibleBook.indexOf("CHAPTER", index+1);
			}
			int secondindex = BibleBook.indexOf("CHAPTER", index+1);
			if (secondindex == -1){
				secondindex = BibleBook.length();
			}
			String CutChapter = BibleBook.substring(index, secondindex);
			textArea.setText(CutChapter);
			textArea.setCaretPosition(0);
		}
	}
	private static String ReadFileToString(String filePath)
	{
		String bbtext = "";

		try
		{
			InputStream inn = Bible.class.getResourceAsStream(filePath);
			BufferedReader in = new BufferedReader(new InputStreamReader(inn));

			// FileReader filer = new FileReader(filePath);
			// BufferedReader in = new BufferedReader(filer);
			while (true) {
				String line = in.readLine();
				bbtext = bbtext + line + "\n";
				if (line == null) { break; }
			}
			in.close();
		}
		catch(IOException except)
		{

		}

		return bbtext;
	}
}

