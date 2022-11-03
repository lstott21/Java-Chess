import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {
	Piece[][] squares;
	boolean blackInCheck = false;
	boolean whiteInCheck = false;

	public Board() {
		squares = new Piece[8][8];

		// white & black pawns
		for (char col = 'A'; col <= 'H'; ++col) {
			String whitePos = String.format("%c2", col);
			setSquare(new Piece(Piece.PieceType.PAWN, Piece.PieceColor.WHITE), whitePos);

			String blackPos = String.format("%c7", col);
			setSquare(new Piece(Piece.PieceType.PAWN, Piece.PieceColor.BLACK), blackPos);
		}
		setSquare(new Piece(Piece.PieceType.ROOK, Piece.PieceColor.WHITE), "A1");
		setSquare(new Piece(Piece.PieceType.ROOK, Piece.PieceColor.WHITE), "H1");
		setSquare(new Piece(Piece.PieceType.KNIGHT, Piece.PieceColor.WHITE), "B1");
		setSquare(new Piece(Piece.PieceType.KNIGHT, Piece.PieceColor.WHITE), "G1");
		setSquare(new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.WHITE), "C1");
		setSquare(new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.WHITE), "F1");
		setSquare(new Piece(Piece.PieceType.QUEEN, Piece.PieceColor.WHITE), "D1");
		setSquare(new Piece(Piece.PieceType.KING, Piece.PieceColor.WHITE), "E1");

		setSquare(new Piece(Piece.PieceType.ROOK, Piece.PieceColor.BLACK), "A8");
		setSquare(new Piece(Piece.PieceType.ROOK, Piece.PieceColor.BLACK), "H8");
		setSquare(new Piece(Piece.PieceType.KNIGHT, Piece.PieceColor.BLACK), "B8");
		setSquare(new Piece(Piece.PieceType.KNIGHT, Piece.PieceColor.BLACK), "G8");
		setSquare(new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.BLACK), "C8");
		setSquare(new Piece(Piece.PieceType.BISHOP, Piece.PieceColor.BLACK), "F8");
		setSquare(new Piece(Piece.PieceType.QUEEN, Piece.PieceColor.BLACK), "D8");
		setSquare(new Piece(Piece.PieceType.KING, Piece.PieceColor.BLACK), "E8");

	}
	
	public String randomComputerMove() {
		Integer[] intArray = { 0, 1, 2, 3, 4, 5, 6, 7 };
		List<Integer> intList = Arrays.asList(intArray);
		Collections.shuffle(intList);
		intList.toArray(intArray);
		
		Integer[] intArray2 = { 0, 1, 2, 3, 4, 5, 6, 7 };
		List<Integer> intList2 = Arrays.asList(intArray2);
		Collections.shuffle(intList2);
		intList2.toArray(intArray2);
		
		for (int col : intArray) {
			for (int row : intArray2) {
				Piece p = squares[col][row];
				if (p == null || p.color != Piece.PieceColor.BLACK) continue;
				String move = hasLegalMoves(col, row);
				if (move == null) continue;
				return move;
			}
		}
		
		return null;
	}
	
	boolean inCheck(Piece.PieceColor color) {
		Piece king = null;
		int col = 0;
		int row = 0;
		for (int i = 0; i <= 7; ++i) {
			for (int j = 0; j <= 7; ++j) {
				king = squares[i][j];
				if (king == null) continue;
				if (king.color == color && king.type == Piece.PieceType.KING) {
					col = i;
					row = j;
					break;
				}
			}
		}
		for (int i = 0; i <= 7; ++i) {
			for (int j = 0; j <= 7; ++j) {
				if (i == row && j == col) continue;
				Piece p = squares[i][j];
				if (p == null || p.color == color) continue;
				if (isLegalMove(i, j, col, row, true)) {
					return true;
				}
			}
		}
		return false;
	}
	
	String hasLegalMoves(int col, int row) {
		for (int i = 0; i <= 7; ++i) {
			for (int j = 0; j <= 7; ++j) {
				if (isLegalMoveWithCheck(col, row, i, j)) {
					return String.format("%c%c-%c%c", 'A' + col, '1' + row, 'A' + i, '1' + j);
				}
			}
		}
		return null;
	}

	public void setSquare(Piece p, String pos) {
		pos = pos.toUpperCase();
		int col = pos.charAt(0) - 'A';
		int row = pos.charAt(1) - '1';
		squares[col][row] = p;
	}

	public String toString() {
		String res = String.format(
				"   __    __    __    __    \r\n" + "8 /%s/%s/%s/%s/%s/%s/%s/%s/\r\n"
						+ "7 /%s/%s/%s/%s/%s/%s/%s/%s/\r\n" + "6 /%s/%s/%s/%s/%s/%s/%s/%s/\r\n"
						+ "5 /%s/%s/%s/%s/%s/%s/%s/%s/\r\n" + "4 /%s/%s/%s/%s/%s/%s/%s/%s/\r\n"
						+ "3 /%s/%s/%s/%s/%s/%s/%s/%s/\r\n" + "2 /%s/%s/%s/%s/%s/%s/%s/%s/\r\n"
						+ "1 /%s/%s/%s/%s/%s/%s/%s/%s/\r\n" + "   a  b  c  d  e  f  g  h",
				squareStr(0, 7), squareStr(1, 7), squareStr(2, 7), squareStr(3, 7), squareStr(4, 7), squareStr(5, 7),
				squareStr(6, 7), squareStr(7, 7), squareStr(0, 6), squareStr(1, 6), squareStr(2, 6), squareStr(3, 6),
				squareStr(4, 6), squareStr(5, 6), squareStr(6, 6), squareStr(7, 6), squareStr(0, 5), squareStr(1, 5),
				squareStr(2, 5), squareStr(3, 5), squareStr(4, 5), squareStr(5, 5), squareStr(6, 5), squareStr(7, 5),
				squareStr(0, 4), squareStr(1, 4), squareStr(2, 4), squareStr(3, 4), squareStr(4, 4), squareStr(5, 4),
				squareStr(6, 4), squareStr(7, 4), squareStr(0, 3), squareStr(1, 3), squareStr(2, 3), squareStr(3, 3),
				squareStr(4, 3), squareStr(5, 3), squareStr(6, 3), squareStr(7, 3), squareStr(0, 2), squareStr(1, 2),
				squareStr(2, 2), squareStr(3, 2), squareStr(4, 2), squareStr(5, 2), squareStr(6, 2), squareStr(7, 2),
				squareStr(0, 1), squareStr(1, 1), squareStr(2, 1), squareStr(3, 1), squareStr(4, 1), squareStr(5, 1),
				squareStr(6, 1), squareStr(7, 1), squareStr(0, 0), squareStr(1, 0), squareStr(2, 0), squareStr(3, 0),
				squareStr(4, 0), squareStr(5, 0), squareStr(6, 0), squareStr(7, 0));
		if (blackInCheck) {
			res += "\r\nBlack is in check.";
		}
		if (whiteInCheck) {
			res += "\r\nWhite is in check.";
		}
		return res;
	}

	String squareStr(int col, int row) {
		Piece p = squares[col][row];
		if (p == null) {
			boolean isLight = (row % 2) == (col % 2);
			return isLight ? "__" : "//";
		} else {
			return p.toString();
		}
	}
	
	void doMove(int col1, int row1, int col2, int row2) {
		Piece p = squares[col1][row1];
		// upgrade to queen if reaches final rank
		if (p.type == Piece.PieceType.PAWN && (row2 == 0 || row2 == 7)) {
			p = new Piece(Piece.PieceType.QUEEN, p.color);
		}
		squares[col1][row1] = null;
		squares[col2][row2] = p;
		
		blackInCheck = inCheck(Piece.PieceColor.BLACK);
		whiteInCheck = inCheck(Piece.PieceColor.WHITE);
	}
	
	boolean isLegalMoveWithCheck(int col1, int row1, int col2, int row2) {
		boolean legal = isLegalMove(col1, row1, col2, row2, false);
		if (!legal) return false;
		Piece p1 = squares[col1][row1];
		Piece p2 = squares[col2][row2];
		boolean checked = p1.color == Piece.PieceColor.BLACK ? blackInCheck : whiteInCheck;
		if (checked) {
			doMove(col1, row1, col2, row2);
			boolean stillInCheck = inCheck(p1.color);
			// undo move
			squares[col1][row1] = p1;
			squares[col2][row2] = p2;
			return !stillInCheck;
		}
		return true;
	}

	boolean isLegalMove(int col1, int row1, int col2, int row2, boolean checkGuarded) {
		// invalid square
		if (col1 < 0 || row1 < 0 || col1 > 7 || row1 > 7)
			return false;
		// invalid square
		if (col2 < 0 || row2 < 0 || col2 > 7 || row2 > 7)
			return false;

		// can't stay still
		if (col1 == col2 && row1 == row2)
			return false;

		Piece p1 = squares[col1][row1];
		Piece p2 = squares[col2][row2];

		// we can only move a piece
		if (p1 == null)
			return false;
		
		if (p2 != null) {
			// if we aren't just checking if a piece is guarded, and color is different, we cannot capture!
			if (!checkGuarded) {
				if (p2.color == p1.color) {
					return false;
				}
				if (p2.type == Piece.PieceType.KING) {
					return false;
				}
			}
		}

		switch (p1.type) {
		case PAWN: {
			int dir = p1.color == Piece.PieceColor.BLACK ? -1 : 1;
			int rowDiff = dir * (row2 - row1);
			int colDiff = Math.abs(col2 - col1);
			if (rowDiff < 1) {
				return false;
			}
			if (checkGuarded && colDiff == 1 && rowDiff == 1) {
				return true;
			}
			if (p2 == null) {
				// if not taking we must be same column
				if (col1 != col2)
					return false;
				// can only ever move pawn 1 or 2 rows
				if (rowDiff > 2)
					return false;
				if (rowDiff == 2) {
					// piece is in the way
					if (squares[col1][row1 + 2 * dir] != null || squares[col1][row1 + 1 * dir] != null)
						return false;
				}
				if (rowDiff == 1) {
					// piece is in the way
					if (squares[col1][row1 + 1 * dir] != null)
						return false;
				}
			} else {
				// we can only take diagonally
				if (colDiff != 1)
					return false;
				if (rowDiff != 1)
					return false;
			}
			return true;
		}

		case KING: {
			if (Math.abs(col2 - col1) > 1)
				return false;
			if (Math.abs(row2 - row1) > 1)
				return false;
			
			for (int i = 0; i <= 7; ++i) {
				for (int j = 0; j <= 7; ++j) {
					if (i == col1 && j == row1) continue;
					if (i == col2 && j == row2) continue;
					Piece guard = squares[i][j]; 
					if (guard == null || guard.color == p1.color) continue;
					if (isLegalMove(i, j, col2, row2, true)) {
						return false;
					}
				}
			}
			return true;
		}
		case ROOK: {
			// we can only move on 1 file
			if (col1 != col2 && row1 != row2)
				return false;
			if (col1 != col2) {
				int min = Math.min(col1, col2);
				int max = Math.max(col1, col2);
				for (int i = min + 1; i < max; ++i) {
					// blocked
					if (squares[i][row1] != null)
						return false;
				}
			}
			if (row1 != row2) {
				int min = Math.min(row1, row2);
				int max = Math.max(row1, row2);
				for (int i = min + 1; i < max; ++i) {
					// blocked
					if (squares[col1][i] != null)
						return false;
				}
			}
			return true;
		}
		case BISHOP: {
			int diffCol = Math.abs(col2 - col1);
			int diffRow = Math.abs(row2 - row1);
			// must be a diagonal
			if (diffCol != diffRow) {
				return false;
			}

			int dirCol = col2 > col1 ? 1 : -1;
			int dirRow = row2 > row1 ? 1 : -1;
			for (int i = 1; i < diffRow; ++i) {
				// blocked
				if (squares[col1 + i * dirCol][row1 + i * dirRow] != null)
					return false;
			}

			return true;
		}

		case QUEEN: {
			int diffCol = Math.abs(col2 - col1);
			int diffRow = Math.abs(row2 - row1);

			// queen is moving like a rook
			if (diffCol == 0 || diffRow == 0) {
				if (col1 != col2) {
					int min = Math.min(col1, col2);
					int max = Math.max(col1, col2);
					for (int i = min + 1; i < max; ++i) {
						// blocked
						if (squares[i][row1] != null)
							return false;
					}
				}
				if (row1 != row2) {
					int min = Math.min(row1, row2);
					int max = Math.max(row1, row2);
					for (int i = min + 1; i < max; ++i) {
						// blocked
						if (squares[col1][i] != null)
							return false;
					}
				}
				return true;
			}

			// queen is moving like a bishop
			if (diffCol != diffRow) {
				return false;
			}

			int dirCol = col2 > col1 ? 1 : -1;
			int dirRow = row2 > row1 ? 1 : -1;

			for (int i = 1; i < diffRow; ++i) {
				// blocked
				if (squares[col1 + i * dirCol][row1 + i * dirRow] != null)
					return false;
			}

			return true;
		}

		case KNIGHT: {
			int diffCol =  col2 - col1;
			int diffRow = row2 - row1;
			return Math.abs(diffCol * diffRow) == 2;
		}

		}
		return true;
	}
}
