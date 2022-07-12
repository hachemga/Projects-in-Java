import java.util.ArrayList;

/**
 * The class Neuron implements a photo receptor for the class Network. There are
 * three types of photo receptors: blue, green and red.
 *
 * @author Vera Röhr
 * @version 1.0
 * @since 2019-01-11
 */
public class Photoreceptor extends Neuron {
    /**
     * Type of the receptor
     */
    String type;

    /**
     * Sets index and type of the photo receptor
     *
     * @param index index of the neuron
     * @param type  type of the receptor
     * @throws RuntimeException if there is a non-existing type of receptor given
     */
    public Photoreceptor(int index, String type) {
        // TODO
        super(index);
        this.type = type;
        this.outgoingsynapses = new ArrayList<Synapse>();
        if ((type != "green") && (type != "red") && (type != "blue")) {
            throw new RuntimeException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws RuntimeException if there is more than one outgoing synapse
     */
    @Override
    public void addSynapse(Synapse synapse) {
        // TODO
        this.outgoingsynapses.add(synapse);
        if (this.outgoingsynapses.size() > 1) {
            throw new RuntimeException();
        }
    }

    /**
     * Converts a light wave into a 3 dimensional neural signal
     *
     * @param wave light wave input
     * @return 3 dimensional neural signal
     */
    public double[] excitation(int wave) {
        // Blau: e^(-(-420 + x)^2/3200)/(40 sqrt(2 π))*100
        // Grün: e^(-(-534 + x)^2/5000)/(50 sqrt(2 π))*125
        // Rot: e^(-(-564 + x)^2/5000)/(50 sqrt(2 π))*125
        double[] excitationrate = new double[3];
        if (this.type.equals("blue")) {
            if (wave < 380 || wave > 530)
                excitationrate[0] = 0;
            else
                excitationrate[0] = Math.exp(-Math.pow((-420 + wave), 2) / 3200) / (40 * Math.sqrt(2 * Math.PI)) * 100;
        } else if (this.type.equals("green")) {
            if (wave < 400 || wave > 750)
                excitationrate[1] = 0;
            else
                excitationrate[1] = Math.exp(-Math.pow((-534 + wave), 2) / 5000) / (50 * Math.sqrt(2 * Math.PI)) * 125;
        } else if (this.type.equals("red")) {
            if (wave < 400 || wave > 750)
                excitationrate[2] = 0;
            else
                excitationrate[2] = Math.exp(-Math.pow((-564 + wave), 2) / 5000) / (50 * Math.sqrt(2 * Math.PI)) * 125;
        }
        return excitationrate;
    }

    /**
     * Averages over the light waves (mixing the colors into one) and sends a neural
     * signal to its outgoing synapse
     *
     * @param signal light wave signal
     * @return 3 dimensional neural signal (after processing)
     */
    @Override
    public double[] integrateSignal(double[] signal) {
        int lightmix = signal.length;
        // transform the given wavelengths into a synaptic signal-
        // parted into blue, green and red
        double[] colormix = new double[3];
        double[] temp = new double[3];
        for (int i = 0; i < lightmix; i++) {
            temp = excitation((int) signal[i]); // the exact wave length is not important- it is all approximated anyway
            for (int c = 0; c < 3; c++) {
                colormix[c] = temp[c] + colormix[c];
            }
        }
        for (int c = 0; c < 3; c++) {
            colormix[c] = colormix[c] / lightmix;
        }

        // transmit the signal to all connected neurons:
        if (this.outgoingsynapses.isEmpty())
            return colormix;
        else
            this.outgoingsynapses.get(0).transmit(colormix);
        return colormix;
    }
}


