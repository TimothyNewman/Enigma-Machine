package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author
 */
public class MovingRotor extends Rotor {
    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initially in its 0 setting (first character of its
     *  alphabet).
     */
    public MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        my_notches = notches;
        _permutation = perm;
    }

    @Override // Use this special tag when updating the behavior of a method this class inherits from Rotor
    public boolean rotates() {
        return true;
    }

    @Override // Use this special tag// when updating the behavior of a method this class inherits from Rotor
    public boolean atNotch() {
        String[] notch_array = my_notches.split("");
        int [] int_array = new int[notch_array.length];
        char[] char_array = new char[notch_array.length];
        for (int i = 0; i < notch_array.length; i++) {
            char_array[i] = notch_array[i].charAt(0);
            int_array[i] = _permutation.alphabet().toInt(char_array[i]);
        }
        for (int y = 0; y < char_array.length; y++) {
            if (setting() == _permutation.wrap(int_array[y])) {
                return true;
            }
        }
        return false;
    }

    @Override // Use this special tag when updating the behavior of a method this class inherits from Rotor
    public void advance() {
        int newposition = _permutation.wrap((super.setting() + 1));
        super.set(newposition);
    }

    private String my_notches;
    private Permutation _permutation;

    // To run this through command line, from the proj0 directory, run the following:
    // javac enigma/Rotor.java enigma/MovingRotor.java enigma/Permutation.java enigma/Alphabet.java enigma/CharacterRange.java enigma/EnigmaException.java
    // java enigma/MovingRotor
    public static void main(String[] args) {
        Permutation perm = new Permutation("(AB) (CDEFGHIJKLMNOPQRSTUVWXYZ)", new CharacterRange('A', 'Z'));
        MovingRotor rotor = new MovingRotor("forward one", perm, "B");

        System.out.println(rotor.name().equals("forward one"));
        System.out.println(rotor.alphabet() == perm.alphabet());
        System.out.println(rotor.permutation() == perm);
        System.out.println(rotor.rotates() == true);
        System.out.println(rotor.reflecting() == false);

        System.out.println(rotor.size() == 26);
        rotor.set(1);
        System.out.println(rotor.setting() == 1);
        System.out.println(rotor.atNotch() == true);
        rotor.set('A');
        System.out.println(rotor.setting() == 0);
        System.out.println(rotor.atNotch() == false);
        System.out.println(rotor.convertForward(0) == 1);
        System.out.println(rotor.convertBackward(1) == 0);
        rotor.advance();
        System.out.println(rotor.setting() == 1);
        System.out.println(rotor.atNotch() == true);
        System.out.println(rotor.convertForward(0) == 25);
        System.out.println(rotor.convertBackward(25) == 0);
    }

}
