package dadomingues.javahelpers;

import java.util.ArrayList;
import java.util.Collections;

public class LastLongs extends ArrayList<Long> {

    private int limit;

    public LastLongs(int limit) {
        this.limit = limit;
    }

    public LastLongs(LastLongs ll) {
        super(ll);
    }

    @Override
    public boolean add(final Long v) {
        if (size() >= limit) {
            remove(0);
        }
        return super.add(v);
    }

    public int count() {
        int count = 0;
        for (long item: this) count++;
        return count;
    }

    public long sum() {
        long sum = 0L;
        for (long item: this) sum += item;
        return sum;
    }

    public long min() {
        long min = get(0);
        for (long item: this) if (item<min) min=item;
        return min;
    }

    public long max() {
        long max = 0L;
        for (long item: this) if (item>max) max=item;
        return max;
    }

    public double sd() {
        double[] standardDeviation = {0.0};
        this.forEach(item -> { standardDeviation[0] += Math.pow(item - mean(), 2); });
        return Math.sqrt(standardDeviation[0]/size());
    }

    public double sdp() {
        return Math.sqrt(varp());
    }

    public double sds() {
        return Math.sqrt(vars());
    }

    public double ss() {
        double ret = 0.0;
        for (Long value : this) {
            double diff = value - mean();
            diff *= diff;
            ret += diff;
        }
        return ret;
    }

    public double vars() {
        return ss() / (size()-1);
    }

    public double varp() {
        return ss() / size();
    }

    public double mean() {
        if (size()==0) return 0;
        return sum() / size();
    }

    public double median() {
        LastLongs ll = copySorted();
        if (ll.size()==0) return 0;
        return ((ll.size()%2)==0) ? (ll.get(ll.size()/2) + ll.get((ll.size()/2)-1)) / 2 : ll.get(ll.size()/2);
    }

    public long mode() {
        if (size()==0) return 0;
        int top = 0;
        long mode = 0L;
        for (int i = 0; i < size(); i++) {
            int count = 0;
            for (int j = 0; j < size(); j++) {
                if (get(j) == get(i)) count++;
            }
            if (count > top) {
                top = count;
                mode = get(i);
            }
        }
        return mode;
    }

    public long percentile(final int p) {
        LastLongs ll = copySorted();
        if (ll.size()==1) return ll.get(0);
        int pos = p * (ll.size() + 1) / 100;
        if (pos < 1) return ll.get(0);
        if (pos > ll.size()) return ll.get(size()-1);
        return ll.get(pos);
    }

    public LastLongs copy() {
        return new LastLongs(this);
    }

    public void sort() {
        Collections.sort(this);
    }

    public LastLongs copySorted() {
        LastLongs ll = new LastLongs(this);
        Collections.sort(ll);
        return ll;
    }

}
