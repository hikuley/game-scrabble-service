package com.sahibinden.service;

import com.sahibinden.domain.Board;
import com.sahibinden.domain.Move;
import com.sahibinden.domain.Word;
import com.sahibinden.exception.GameException;
import com.sahibinden.model.Content;
import com.sahibinden.model.Letter;
import com.sahibinden.model.ResponseModel;
import com.sahibinden.repository.BoardRepository;
import com.sahibinden.repository.MoveRepository;
import com.sahibinden.repository.WordRepository;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameServiceImpl implements GameService {

    final static Logger logger = Logger.getLogger(GameServiceImpl.class);

    @Inject
    private BoardRepository boardRepository;

    @Inject
    private MoveRepository moveRepository;

    @Inject
    private WordRepository wordRepository;

    /**
     * @param board
     * @return
     */

    @Override
    public Long createBoard(Board board) {

        board.setSequence(0);

        board.setActive(Boolean.TRUE);

        Board savedBoard = boardRepository.create(board);

        logger.info("Created new Board.");

        return savedBoard.getId();
    }

    /**
     * @param id
     * @param moves
     * @return
     */

    public ResponseModel play(long id, List<Move> moves) throws GameException {
        Board board = boardRepository.findById(id);

        if (!board.getActive()) {

            logger.info("Board is not active.");

            throw new GameException("Board is not active.");
        }

        Integer direction = validateDirection(moves);

        if (direction == null) {

            logger.info("Wrong direction");

            throw new GameException("Wrong direction");
        }

        List<Move> existingMoves = moveRepository.getExistingMoves(board);

        Boolean isValidStartPoint = validateStartPoint(existingMoves, moves);

        if (!isValidStartPoint) {

            logger.info("Invalid start point");

            throw new GameException("Invalid start point");
        }

        String wordValue;

        try {
            wordValue = checkIsConsecutiveAndGetWord(existingMoves, moves, direction);
        } catch (Exception e) {
            throw new GameException(e.getMessage());
        }

        HashMap<String, Integer> dictionary = getDictionary();

        if (dictionary.get(wordValue) == null) {

            logger.info("Word " + wordValue + " does not exist in dictionary.");

            throw new GameException("Word " + wordValue + " does not exist in dictionary.");
        }

        int totalPoint = 0;

        try {
            totalPoint = calculatePointAndSetBoard(moves, board);
        } catch (Exception e) {
            return new ResponseModel(e.getMessage());
        }

        board.setSequence(board.getSequence() + 1);

        boardRepository.update(board);


        for (Move move : moves) {
            if (move.getId() == null) {
                move.setBoard(board);
                move.setSequence(board.getSequence());
                moveRepository.update(move);
            }
        }

        Word word = new Word();
        word.setBoard(board);
        word.setPoint(totalPoint);
        word.setValue(wordValue);
        wordRepository.create(word);


        return new ResponseModel("Your move");

    }


    /**
     * @return
     */

    private HashMap<String, Integer> getDictionary() {

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("scrabble_turkish_dictionary.txt").getFile());

        HashMap<String, Integer> dictionary = new HashMap<>();

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();

                dictionary.putIfAbsent(word, 1);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dictionary;

    }


    /**
     * 1 == Horizontal
     * 2 == Vertical
     *
     * @param moves
     * @return
     */

    private Integer validateDirection(List<Move> moves) {

        Move firstMove = moves.get(0);
        Move secondMove = moves.get(1);

        if (firstMove.getY().equals(secondMove.getY()) && secondMove.getX().compareTo(firstMove.getX()) > 0) {
            return 0;
        } else if (firstMove.getX().equals(secondMove.getX()) && firstMove.getY().compareTo(secondMove.getX()) > 0) {
            return 1;
        }

        return null;
    }


    /**
     * @param existingMoves
     * @param moves
     * @return
     * @throws Exception
     */
    private boolean validateStartPoint(List<Move> existingMoves, List<Move> moves) throws GameException {

        Boolean isFirstMove = existingMoves.size() < 1;

        for (Move move : moves) {

            if (Math.abs(move.getX()) > 7 || Math.abs(move.getY()) > 7) {

                logger.error("Out of bounds");

                throw new GameException("Out of bounds");
            }


            if (isFirstMove) {

                if (move.getX().equals(0) && move.getY().equals(0)) {
                    return true;
                }

            } else {
                // Check if start point is valid
                for (Move existingMove : existingMoves) {

                    if (move.getX().equals(existingMove.getX())) {
                        if (Math.abs(move.getY().compareTo(existingMove.getY())) == 1) {
                            return true;
                        }
                    } else if (move.getY().equals(existingMove.getY())) {
                        if (Math.abs(move.getX().compareTo(existingMove.getX())) == 1) {
                            return true;
                        }
                    }

                }
            }

        }

        return false;

    }


    /**
     * @return
     */

    private HashMap<String, Letter> getLetters() {

        HashMap<String, Letter> letterMap = new HashMap<>();

        letterMap.put("a", new Letter("a", 1));
        letterMap.put("b", new Letter("b", 3));
        letterMap.put("c", new Letter("c", 4));
        letterMap.put("ç", new Letter("ç", 4));
        letterMap.put("d", new Letter("d", 3));
        letterMap.put("e", new Letter("e", 1));
        letterMap.put("f", new Letter("f", 7));
        letterMap.put("g", new Letter("g", 5));
        letterMap.put("ğ", new Letter("ğ", 8));
        letterMap.put("h", new Letter("h", 5));
        letterMap.put("ı", new Letter("ı", 2));
        letterMap.put("i", new Letter("i", 1));
        letterMap.put("j", new Letter("j", 10));
        letterMap.put("k", new Letter("k", 1));
        letterMap.put("l", new Letter("l", 1));
        letterMap.put("m", new Letter("m", 2));
        letterMap.put("n", new Letter("n", 1));
        letterMap.put("o", new Letter("o", 2));
        letterMap.put("ö", new Letter("ö", 7));
        letterMap.put("p", new Letter("p", 5));
        letterMap.put("r", new Letter("r", 1));
        letterMap.put("s", new Letter("s", 2));
        letterMap.put("ş", new Letter("ş", 4));
        letterMap.put("t", new Letter("t", 1));
        letterMap.put("u", new Letter("u", 2));
        letterMap.put("ü", new Letter("ü", 3));
        letterMap.put("v", new Letter("v", 7));
        letterMap.put("y", new Letter("y", 3));
        letterMap.put("z", new Letter("z", 4));

        return letterMap;
    }

    /**
     * @param existingMoves
     * @param moves
     * @param direction
     * @return
     * @throws Exception
     */

    private String checkIsConsecutiveAndGetWord(List<Move> existingMoves, List<Move> moves, Integer direction)
            throws Exception {

        Move firstMove = moves.get(0);

        // Horizontal
        if (direction == 0) {

            for (Move existingMove : existingMoves) {
                if (firstMove.getY().equals(existingMove.getY())) {
                    moves.add(existingMove);
                }
            }

            Collections.sort(moves, new Comparator<Move>() {
                @Override
                public int compare(Move move1, Move move2) {
                    if (move1.equals(move2)) {
                        return 0;
                    }
                    return move1.getX().compareTo(move2.getX());
                }
            });

        } else {

            for (Move existingMove : existingMoves) {
                if (firstMove.getX().equals(existingMove.getX())) {
                    moves.add(existingMove);
                }
            }

            // Vertical
            Collections.sort(moves, new Comparator<Move>() {
                @Override
                public int compare(Move move1, Move move2) {
                    if (move1.equals(move2)) {
                        return 0;
                    }
                    return move2.getY().compareTo(move1.getY());
                }
            });
        }

        StringBuilder sb = new StringBuilder(moves.get(0).getValue());

        for (int i = 1; i < moves.size(); i++) {

            Move previousMove = moves.get(i - 1);
            Move move = moves.get(i);

            if (direction == 0 && move.getX() - previousMove.getX() != 1) {

                logger.error("Letter must be consecutive.");

                throw new Exception("Letter must be consecutive.");
            } else if (direction == 1 && previousMove.getY() - move.getY() != 1) {

                logger.error("Letter must be consecutive.");

                throw new Exception("Letter must be consecutive.");
            }

            sb.append(move.getValue());
        }

        return sb.toString();

    }

    private int calculatePointAndSetBoard(List<Move> moves, Board board) {

        HashMap<String, Letter> letterMap = getLetters();

        int totalPoint = 0;

        for (Move move : moves) {

            Letter letter = letterMap.get(move.getValue());

            totalPoint += letter.getPoint();
        }

        return totalPoint;

    }

    @Override
    public void drawBoard(Long id) {

        Board board = new Board();

        board.setId(id);

        List<Move> existingMoves = moveRepository.getExistingMoves(board);

        this._drawBoard(existingMoves);

    }

    public void _drawBoard(List<Move> existingMoves) {

        Map<String, Letter> moveMap = new HashMap<>();

        HashMap<String, Letter> letterMap = getLetters();

        for (Move existingMove : existingMoves) {
            String coord = existingMove.getX() + ":" + existingMove.getY();

            moveMap.putIfAbsent(coord, letterMap.get(existingMove.getValue()));
        }

        System.out.print(" ");

        for (int x = -7; x < 8; x++) {

            System.out.print(x);

            if (x < -1) {
                System.out.print("  ");
            } else {
                System.out.print("   ");
            }
        }

        System.out.println();

        for (int y = 7; y > -8; y--) {
            System.out.println("-------------------------------------------------------------");
            System.out.print("| ");

            for (int x = -7; x < 8; x++) {
                Letter letter = moveMap.get(x + ":" + y);

                String value = letter == null ? " " : letter.getValue();

                System.out.print(value + " | ");
            }

            System.out.println(y);
        }
        System.out.println("-------------------------------------------------------------");
    }

    @Override
    public List<Word> getWords(long id) {

        List<Word> wordList = wordRepository.getWords(id);

        return wordList;

    }

    /**
     * @param id
     * @param sequence
     * @return
     */

    @Override
    public Content getBoardContent(long id, int sequence) {


        List<Move> moves = moveRepository.getExistingMovesByIdandSequence(id, sequence);

        List<Content> contents = new ArrayList<>();

        Map<Integer, List<Move>> sMap = new HashMap<>();

        for (Move move : moves) {
            if (sequence >= move.getSequence()) {
                sMap.putIfAbsent(move.getSequence(), new ArrayList<>());
                sMap.get(move.getSequence()).add(move);
            }
        }

        List<String> words = new ArrayList<>();

        for (List<Move> v : sMap.values()) {
            words.add(getWord(v));
        }

        Content content = new Content();
        content.setSequence(sequence);
        content.setWords(words);

        contents.add(content);

        return content;
    }

    @Override
    public Response setStatus(long id) {

        Board board = boardRepository.findById(id);

        board.setActive(Boolean.FALSE);

        boardRepository.update(board);

        return Response.ok().build();
    }

    private String getWord(List<Move> moves) {

        StringBuilder sb = new StringBuilder();

        for (Move move : moves) {
            sb.append(move.getValue());
        }

        return sb.toString();
    }


}
