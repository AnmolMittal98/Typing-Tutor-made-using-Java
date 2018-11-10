package C23;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Typing_Tutor extends JFrame implements ActionListener, KeyListener {

	private JLabel lblTimer;
	private JLabel lblScore;
	private JLabel lblWord;

	private JTextField textWord;
	private JButton btnstart;
	private JButton btnstop;

	private Timer Clocktimer = null;
	private Timer Wordtimer = null;
	private boolean running = false;
	private int timeRemaining = 0;
	private int score = 0;
	private String[] words = null;

	public Typing_Tutor(String[] args) {

		words = args;

		GridLayout layout = new GridLayout(3, 2);
		super.setLayout(layout);

		Font font = new Font("Algerian", 1, 100);

		lblTimer = new JLabel("Time");
		lblTimer.setFont(font);
		super.add(lblTimer);

		lblScore = new JLabel("Score");
		lblScore.setFont(font);
		super.add(lblScore);

		lblWord = new JLabel("");
		lblWord.setFont(font);
		super.add(lblWord);

		textWord = new JTextField("");
		textWord.setFont(font);
		textWord.addKeyListener(this);
		super.add(textWord);

		btnstart = new JButton("Start");
		btnstart.setFont(font);
		btnstart.addActionListener(this);
		super.add(btnstart);

		btnstop = new JButton("Stop");
		btnstop.setFont(font);
		btnstop.addActionListener(this);
		super.add(btnstop);

		super.setTitle("TYPING TUTOR");
		super.setExtendedState(MAXIMIZED_BOTH);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setVisible(true);

		setupthegame();

	}

	private void setupthegame() {

		Clocktimer = new Timer(1000, this);
		Clocktimer.setInitialDelay(0);

		Wordtimer = new Timer(3000, this);
		Wordtimer.setInitialDelay(0);

		running = false;
		score = 0;
		timeRemaining = 50;

		lblScore.setText("Score: " + score);
		lblTimer.setText("Timer: " + timeRemaining);

		lblWord.setText("");
		textWord.setText("");

		btnstop.setEnabled(false);
		textWord.setEnabled(false);

	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnstart) {
			handlestart();
		} else if (e.getSource() == btnstop) {
			handlestop();
		} else if (e.getSource() == Wordtimer) {
			handlewordtime();
		} else if (e.getSource() == Clocktimer) {
			handleclocktime();
		}

	}

	private void handleclocktime() {
		timeRemaining--;
		lblTimer.setText("Time: " + timeRemaining);

		if (timeRemaining == -1) {

			handlestop();
			return;
		}

	}

	private void handlewordtime() {
		String actual = textWord.getText();
		String expected = lblWord.getText();
		if (expected.length() > 0 && actual.equals(expected)) {
			score++;
		}
		lblScore.setText("Score: " + score);

		int ridx = (int) (Math.random() * words.length);
		lblWord.setText(words[ridx]);
		textWord.setText("");
	}

	private void handlestart() {
		if (running == false) {
			textWord.setText("*****");
			Wordtimer.start();
			Clocktimer.start();
			running = true;
			btnstart.setText("PAUSE");
			textWord.setEnabled(true);
			btnstop.setEnabled(true);

			textWord.setFocusCycleRoot(true);
			super.nextFocus();
		} else {

			Wordtimer.stop();
			Clocktimer.stop();
			running = false;
			btnstart.setText("Start");
			textWord.setEnabled(false);
			btnstop.setEnabled(false);

		}
	}

	private void handlestop() {
		Clocktimer.stop();
		Wordtimer.stop();
		int choice = JOptionPane.showConfirmDialog(this, "REPLAY");

		if (choice == JOptionPane.YES_OPTION) {
			setupthegame();
		} else if (choice == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(this, "FINAL SCORE : " + score);
			super.dispose();
		} else if (choice == JOptionPane.CANCEL_OPTION) {
			if (timeRemaining > 0) {
				Clocktimer.start();
				Wordtimer.start();
			} else {
				setupthegame();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		String actual = textWord.getText();
		String expected = lblWord.getText();
		if (expected.length() > 0 && actual.equals(expected)) {
			score++;

			lblScore.setText("Score: " + score);

			int ridx = (int) (Math.random() * words.length);
			lblWord.setText(words[ridx]);
			textWord.setText("");
			Wordtimer.restart();
		}
	}

}
