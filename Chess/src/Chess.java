import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Chess {
	enum GameState {
		IN_PROGRESS,
		WON,
		LOST
	}
	public static void main(String[] args) {
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		Board board = new Board();
		
		while (getGameState() == GameState.IN_PROGRESS) {
			System.out.println(board.toString());
			System.out.println("Enter your next move:");
			String nextMove = scanner.nextLine().toUpperCase().replaceAll("[^0-9A-Z]", "");
			if (nextMove.length() != 4) {
				System.out.println("Error, move must be in the format XX-XX (example: A2-A3) Try again!");
				continue;
			}
			//System.out.println(nextMove);
			int col1 = nextMove.charAt(0) - 'A';
			int row1 = nextMove.charAt(1) - '1';
			int col2 = nextMove.charAt(2) - 'A';
			int row2 = nextMove.charAt(3) - '1';
			if (!board.isLegalMoveWithCheck(col1, row1, col2, row2)) {
				System.out.println("Error, illegal move. Try again!");
				continue;
			}
			board.doMove(col1, row1, col2, row2);
			System.out.println(board.toString());
			
			System.out.println("I am thinking of my next move....");
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
			}
			String computerMove = board.randomComputerMove();
			if (computerMove == null) {
				System.out.println("I (Black) have been checkmated! Nice Job!");
				return;
			}
			System.out.println(String.format("I move %s", computerMove));
			computerMove = computerMove.toUpperCase().replaceAll("[^0-9A-Z]", "");
			col1 = computerMove.charAt(0) - 'A';
			row1 = computerMove.charAt(1) - '1';
			col2 = computerMove.charAt(2) - 'A';
			row2 = computerMove.charAt(3) - '1';
			board.doMove(col1, row1, col2, row2);
		}
		System.out.print(board);
	}
	
	static GameState getGameState() {
		return GameState.IN_PROGRESS;
	}
}
