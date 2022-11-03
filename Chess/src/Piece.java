
public class Piece {
	public enum PieceType {
		PAWN,
		KNIGHT,
		BISHOP,
		ROOK,
		QUEEN,
		KING
	}
	public enum PieceColor {
		WHITE,
		BLACK
	}
	
	PieceType type;
	PieceColor color;
	
	
	public Piece(PieceType type, PieceColor color) {
		this.type = type;
		this.color = color;
	}
	
	public String toString() {
		String colorStr = color == PieceColor.WHITE ? "W" : "B";
		String pieceStr = "";
		switch (type) {
		case PAWN:
			pieceStr = "P";
			break;
			
		case KNIGHT:
			pieceStr = "N";
			break;
			
		case BISHOP:
			pieceStr = "B";
			break;
			
		case ROOK:
			pieceStr = "R";
			break;
		
		case QUEEN:
			pieceStr = "Q";
			break;
			
		case KING:
			pieceStr = "K";
			break;
		}
		
		return colorStr + pieceStr;
	}
}
