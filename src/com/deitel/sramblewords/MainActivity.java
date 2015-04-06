package com.deitel.sramblewords;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


	public class MainActivity extends Activity {
		
		private String scrambledWord;
		private int wordNumber =  0;
		private int playerScore = 0;
		private int playerLevel = 3;
		private EditText enterWord;
		private TextView wordTV, levelTV, scoreTV;
		private CountDownTimer countDownTimer;
		public static Map<Integer, Integer> levelScore = new HashMap<Integer, Integer>();

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			Data data = new Data();

			wordTV = (TextView) findViewById(R.id.wordText);
			final TextView timerTV = (TextView) findViewById(R.id.timerText);
			enterWord = (EditText) findViewById(R.id.enterWordET);
			levelTV = (TextView) findViewById(R.id.levelTV);
			scoreTV = (TextView) findViewById(R.id.levelSScoreTV);

			scrambledWord = Data.threeLetterWord.get(wordNumber);
			wordTV.setText(scramble(scrambledWord));
			enterWord.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence enteredwords, int start,
						int before, int count) {

					if (scrambledWord.length() == enteredwords.toString().length()) {
						wordNumber++;

						if (wordNumber == 5) {
							wordNumber = 0;
							if (playerScore > 3) {
								playerLevel++;
								playerScore = 0;
								countDownTimer.cancel();
								if (playerLevel > 7) {
									Intent intent = new Intent(MainActivity.this,
											ResultView.class);
									startActivity(intent);
								} else {
									levelTV.setText("" + (playerLevel - 2));
									countDownTimer.start();
								}
							} else {
								playerScore = 0;
								scoreTV.setText("You have Lost, Please try again");
							}
						}

						if (scrambledWord.equalsIgnoreCase(enteredwords.toString())) {
							playerScore++;
							levelScore.put(playerLevel, playerScore);
							scoreTV.setText("" + playerScore);
						}
						if (playerLevel < 8) {
							scrambledWord = getWordByLevel(playerLevel);
							wordTV.setText(scramble(scrambledWord.toLowerCase()));

							enterWord.setText("");
						}
					}

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {

				}
			});
			levelTV.setText("" + (playerLevel - 2));
			countDownTimer = new CountDownTimer(60000, 1000) {

				public void onTick(long millisUntilFinished) {
					timerTV.setText("seconds remaining: " + millisUntilFinished
							/ 1000);
				}

				public void onFinish() {
					timerTV.setText("You have Lost, Please try again");
				}
			}.start();
		}

		
		public String scramble(String word) {
			String scrambleWord = "";
			int wordLength = word.length();
			if (wordLength <= 2) {
				return word;
			} else {
				for (int i = 0; i <= wordLength - 1; i++) {
					char letter = word.charAt(i);
					if (i == (int) ((wordLength / 2) - 1)) // finding the aprox
															// location of the
															// middle letter and
															// evaluating to i
					{
						scrambleWord = scrambleWord + word.charAt(i + 1)
								+ word.charAt(i);// scramble the letters inside the
													// word
						letter = word.charAt(i++); // pass the upcoming letter as we
													// used it in the previous
													// statement
					} else {
						scrambleWord = scrambleWord + letter;
					}

				}
			}
			return scrambleWord;
		}

		public String getWordByLevel(int level) {
			String word = "";
			switch (level) {
			case 3:
				word = scrambledWord = Data.threeLetterWord.get(wordNumber);
				break;
			case 4:
				word = scrambledWord = Data.fourLetterWord.get(wordNumber);
				break;
			case 5:
				word = scrambledWord = Data.fiveWord.get(wordNumber);
				break;
			case 6:
				word = scrambledWord = Data.sixWord.get(wordNumber);
				break;
			case 7:
				word = scrambledWord = Data.sevenWord.get(wordNumber);
				break;

			}
			return word;
		}

		public int getRandom(int from, int to) {
			if (from < to) {
				return from + new Random().nextInt(Math.abs(to - from));
			}
			return from - new Random().nextInt(Math.abs(to - from));
		}
	}
