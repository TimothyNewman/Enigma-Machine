package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author
 */
public class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    public Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        this.cycles = cycles;
        forwardarray = new int[alphabet.size()];
        backwardarray = new int[alphabet.size()];
        for (int i = 0; i < forwardarray.length; i++) {
            forwardarray[i] = i;
            backwardarray[i] = i;
        }
        int firstletter = 0;
        for (int i = 0; i < cycles.length(); i++){
            if (cycles.charAt(i) == '(') {
                firstletter = alphabet.toInt(cycles.charAt(i + 1));
            }
            else if (cycles.charAt(i) == ')'){
                forwardarray[alphabet.toInt(cycles.charAt(i - 1))] = firstletter;
            }
            else if (alphabet.contains(cycles.charAt(i)) == true && !(cycles.charAt(i + 1) == ')')) {
                forwardarray[alphabet.toInt(cycles.charAt(i))] = alphabet.toInt(cycles.charAt(i + 1));
            }

        }
        for (int i = 0; i < cycles.length(); i++){
            if (cycles.charAt(i) == '(') {
                firstletter = alphabet.toInt(cycles.charAt(i + 1));
            }
            else if (cycles.charAt(i) == ')'){
                backwardarray[firstletter] = alphabet.toInt(cycles.charAt(i - 1));
            }
            else if (alphabet.contains(cycles.charAt(i)) == true && !(cycles.charAt(i + 1) == ')')) {
                backwardarray[alphabet.toInt(cycles.charAt(i + 1))] = alphabet.toInt(cycles.charAt(i));
            }
        }
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    public int size() {
        return _alphabet.size();
    }

    /** Return the index result of applying this permutation to the character
     *  at index P in ALPHABET. */
    public int permute(int p) {
        return forwardarray[wrap(p)];
    }

    /** Return the index result of applying the inverse of this permutation
     *  to the character at index C in ALPHABET. */
    public int invert(int c) {
        return backwardarray[wrap(c)];
    }

    /** Return the character result of applying this permutation to the index
     * of character P in ALPHABET. */
    public char permute(char p) {
        int index = _alphabet.toInt(p);
        int grabbed = forwardarray[wrap(index)];
        return _alphabet.toChar(grabbed);
    }

    /** Return the character result of applying the inverse of this permutation
	 * to the index of character P in ALPHABET. */
    public char invert(char c) {
        int index = _alphabet.toInt(c);
        int grabbed = backwardarray[wrap(index)];
        return _alphabet.toChar(grabbed);
    }

    /** Return the alphabet used to initialize this Permutation. */
    public Alphabet alphabet() {
        return _alphabet;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    private String cycles;
    public int[] forwardarray;
    public int[] backwardarray;


    // To run this through command line, from the proj0 directory, run the following:
    // javac enigma/Permutation.java enigma/Alphabet.java enigma/CharacterRange.java enigma/EnigmaException.java
    // java enigma/Permutation
    public static void main(String[] args) {
        Permutation perm = new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", new CharacterRange('A', 'Z'));
        System.out.println(perm.permute('A') == 'B');
        System.out.println(perm.invert('A'));
        System.out.println(perm.invert('Z') );
        System.out.println(perm.invert('R'));
        System.out.println(perm.permute(0) == 1);
        System.out.println(perm.invert(17));
    }
}
