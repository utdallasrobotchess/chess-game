package chess.engine;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Tests the functionality of the ChessBoardBuilder, including the building of all 
 * Squares in the proper places and properly assigning actualNeighbors in all directions
 * @author Ryan J. Marcotte
 */
public class ChessBoardBuilderTest {
	private ChessBoard board;
	private String errorMessage;
	private Square testSquare, actualNeighbor;

	@Before
	public void initialize() {
		board = ChessBoard.generateChessBoard();
	}
    
	/*
	 * Ensures that squares 0-63 have been built as interior squares
	 */ 
    @Test
    public void testInteriorSquaresBuilt() {        
        Square actualSquare;
		
		for(int i = 0; i < 64; i++) {
            actualSquare = board.getSquareAt(i);
            assert(actualSquare instanceof InteriorSquare);
            assertEquals(i, actualSquare.getNumericalLocation());
        }
    }
    
	/*
	 * Ensures that squares 64-99 have been built as perimeter squares
	 */
    @Test
    public void testPerimeterSquaresBuilt() {  
		Square actualSquare;

        for(int i =  64; i < 100; i++) {
            actualSquare = board.getSquareAt(i);
            assert(actualSquare instanceof PerimeterSquare);
            assertEquals(i, actualSquare.getNumericalLocation());
        }
    }
    
    @Test
    public void testNorthNeighbors() {
        int[] expectedNorthNeighbors = {
            65, 66, 67, 68, 69, 70, 71, 72,  0,  1,
             2,  3,  4,  5,  6,  7,  8,  9, 10, 11,
            12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
            22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
            32, 33, 34, 35, 36, 37, 38, 39, 40, 41,
            42, 43, 44, 45, 46, 47, 48, 49, 50, 51,
            52, 53, 54, 55, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 64, 73, 74, 75, 76, 77,
            78, 79, 80, 81, 82, 83, 84, 85, 86, 87,
            88, 56, 57, 58, 59, 60, 61, 62, 63, 89
        };
        
        for(int i = 0; i < 100; i++) {
            testSquare = board.getSquareAt(i);
            actualNeighbor = testSquare.getNeighborInDirection(0);
            errorMessage = "Unexpected north neighbor at location " + i;
            assertEquals(errorMessage, expectedNorthNeighbors[i], actualNeighbor.getNumericalLocation());
        }
    }
    
    @Test
    public void testNorthEastNeighbors() {
        int[] expectedNorthEastNeighbors = {
            66, 67, 68, 69, 70, 71, 72, 73,  1,  2,
             3,  4,  5,  6,  7, 75,  9, 10, 11, 12,
            13, 14, 15, 77, 17, 18, 19, 20, 21, 22,
            23, 79, 25, 26, 27, 28, 29, 30, 31, 81,
            33, 34, 35, 36, 37, 38, 39, 83, 41, 42,
            43, 44, 45, 46, 47, 85, 49, 50, 51, 52,
            53, 54, 55, 87, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 65, -1,  0, -1,  8, -1,
            16, -1, 24, -1, 32, -1, 40, -1, 48, -1,
            56, 57, 58, 59, 60, 61, 62, 63, 89, -1
        };
        
        for(int i = 0; i < 100; i++) {
            testSquare = board.getSquareAt(i);
            actualNeighbor = testSquare.getNeighborInDirection(1);
            errorMessage = "Unexpected northeast neighbor at location " + i;
            assertEquals(errorMessage, expectedNorthEastNeighbors[i], actualNeighbor.getNumericalLocation());
        }        
    }
    
    @Test
    public void testEastNeighbors() {
        int[] expectedEastNeighbors = {
             1,  2,  3,  4,  5,  6,  7, 75,  9, 10,
            11, 12, 13, 14, 15, 77, 17, 18, 19, 20,
            21, 22, 23, 79, 25, 26, 27, 28, 29, 30,
            31, 81, 33, 34, 35, 36, 37, 38, 39, 83,
            41, 42, 43, 44, 45, 46, 47, 85, 49, 50,
            51, 52, 53, 54, 55, 87, 57, 58, 59, 60,
            61, 62, 63, 89, 65, 66, 67, 68, 69, 70,
            71, 72, 73, -1,  0, -1,  8, -1, 16, -1,
            24, -1, 32, -1, 40, -1, 48, -1, 56, -1,
            91, 92, 93, 94, 95, 96, 97, 98, 99, -1
        };

        for(int i = 0; i < 100; i++) {
            testSquare = board.getSquareAt(i);
            actualNeighbor = testSquare.getNeighborInDirection(2);
            errorMessage = "Unexpected east neighbor at location " + i;
            assertEquals(errorMessage, expectedEastNeighbors[i], actualNeighbor.getNumericalLocation());
        }
    }
    
    @Test
    public void testSouthEastNeighbors() {
        int[] expectedSouthEastNeighbors = {
             9, 10, 11, 12, 13, 14, 15, 77, 17, 18,
            19, 20, 21, 22, 23, 79, 25, 26, 27, 28,
            29, 30, 31, 81, 33, 34, 35, 36, 37, 38,
            39, 83, 41, 42, 43, 44, 45, 46, 47, 85,
            49, 50, 51, 52, 53, 54, 55, 87, 57, 58,
            59, 60, 61, 62, 63, 89, 92, 93, 94, 95,
            96, 97, 98, 99,  0,  1,  2,  3,  4,  5,
            6,  7,  75, -1,  8, -1, 16, -1, 24, -1,
            32, -1, 40, -1, 48, -1, 56, -1, 91, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
        };
        
        for(int i = 0; i < 100; i++) {
            testSquare = board.getSquareAt(i);
            actualNeighbor = testSquare.getNeighborInDirection(3);
            errorMessage = "Unexpected southeast neighbor at location " + i;
            assertEquals(errorMessage, expectedSouthEastNeighbors[i], actualNeighbor.getNumericalLocation());
        }
    }
    
    @Test
    public void testSouthNeighbors() {
        int[] expectedSouthNeighbors = {
             8,  9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
            28, 29, 30, 31, 32, 33, 34, 35, 36, 37,
            38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
            48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
            58, 59, 60, 61, 62, 63, 91, 92, 93, 94,
            95, 96, 97, 98, 74,  0,  1,  2,  3,  4,
             5,  6,  7, 75, 76, 77, 78, 79, 80, 81,
            82, 83, 84, 85, 86, 87, 88, 89, 90, 99,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
        };
 
        for(int i = 0; i < 100; i++) {
            testSquare = board.getSquareAt(i);
            actualNeighbor = testSquare.getNeighborInDirection(4);
            errorMessage = "Unexpected south neighbor at location " + i;
            assertEquals(errorMessage, expectedSouthNeighbors[i], actualNeighbor.getNumericalLocation());
        }
    }
    
    @Test
    public void testSouthWestNeighbors() {
        int[] expectedSouthWestNeighbors = {
            76,  8,  9, 10, 11, 12, 13, 14, 78, 16,
            17, 18, 19, 20, 21, 22, 80, 24, 25, 26,
            27, 28, 29, 30, 82, 32, 33, 34, 35, 36,
            37, 38, 84, 40, 41, 42, 43, 44, 45, 46,
            86, 48, 49, 50, 51, 52, 53, 54, 88, 56,
            57, 58, 59, 60, 61, 62, 90, 91, 92, 93,
            94, 95, 96, 97, -1, 74,  0,  1,  2,  3,
             4,  5,  6,  7, -1, 15, -1, 23, -1, 31,
            -1, 39, -1, 47, -1, 55, -1, 63, -1, 98,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
        };

        for(int i = 0; i < 100; i++) {
            testSquare = board.getSquareAt(i);
            actualNeighbor = testSquare.getNeighborInDirection(5);
            errorMessage = "Unexpected southwest neighbor at location " + i;
            assertEquals(errorMessage, expectedSouthWestNeighbors[i], actualNeighbor.getNumericalLocation());
        }
    }
    
    @Test
    public void testWestNeighbors() {
        int[] expectedWestNeighbors = {
            74,  0,  1,  2,  3,  4,  5,  6, 76,  8,
             9, 10, 11, 12, 13, 14, 78, 16, 17, 18,
            19, 20, 21, 22, 80, 24, 25, 26, 27, 28,
            29, 30, 82, 32, 33, 34, 35, 36, 37, 38,
            84, 40, 41, 42, 43, 44, 45, 46, 86, 48,
            49, 50, 51, 52, 53, 54, 88, 56, 57, 58,
            59, 60, 61, 62, -1, 64, 65, 66, 67, 68,
            69, 70, 71, 72, -1,  7, -1, 15, -1, 23,
            -1, 31, -1, 39, -1, 47, -1, 55, -1, 63,
            -1, 90, 91, 92, 93, 94, 95, 96, 97, 98
        };

		for(int i = 0; i < 100; i++) {
            testSquare = board.getSquareAt(i);
            actualNeighbor = testSquare.getNeighborInDirection(6);
            errorMessage = "Unexpected west neighbor at location " + i;
            assertEquals(errorMessage, expectedWestNeighbors[i], actualNeighbor.getNumericalLocation());
        }
    }
    
    @Test
    public void testNorthWestNeighbors() {
        int[] expectedNorthWestNeighbors = {
            64, 65, 66, 67, 68, 69, 70, 71, 74,  0,
             1,  2,  3,  4,  5,  6, 76,  8,  9, 10,
            11, 12, 13, 14, 78, 16, 17, 18, 19, 20,
            21, 22, 80, 24, 25, 26, 27, 28, 29, 30,
            82, 32, 33, 34, 35, 36, 37, 38, 84, 40,
            41, 42, 43, 44, 45, 46, 86, 48, 49, 50,
            51, 52, 53, 54, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 72, -1,  7, -1, 15,
            -1, 23, -1, 31, -1, 39, -1, 47, -1, 55,
            -1, 88, 56, 57, 58, 59, 60, 61, 62, 63
        };
       
        for(int i = 0; i < 100; i++) {
            testSquare = board.getSquareAt(i);
            actualNeighbor = testSquare.getNeighborInDirection(7);
            errorMessage = "Unexpected northwest neighbor at location" + i;
            assertEquals(errorMessage, expectedNorthWestNeighbors[i], actualNeighbor.getNumericalLocation());
        }
    }
    
}
