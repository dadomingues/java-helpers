package dadomingues.javahelpers;

import java.util.*;

public class LastHashLongs extends LinkedHashMap<String,Long> {

    private int limit;

    public LastHashLongs(int limit) {
        this.limit = limit;
    }

    public LastHashLongs(LastHashLongs ll) {
        super(ll);
    }

    public LastHashLongs(Map<String, Long> map) {
        super(map);
    }

    public void load(final Map<String, Long> map) {
        clear();
        map.forEach((k, v) -> {
            put(k, v);
        });
    }

    public String getFirstKey() {
        for (Map.Entry<String, Long> entry : entrySet()) {
            return entry.getKey();
        }
        return null;
    }

    @Override
    public Long put(final String k, final Long v) {
        if (size() >= limit) {
            remove(getFirstKey());
        }
        return super.put(k,v);
    }

    public int count() {
        return size();
    }

    public long sum() {
        long sum = 0L;
        for (Long item: this.values()) sum += item;
        return sum;
    }

    public long min() {
        return getValuesSorted().get(0);
    }

    public long max() {
        return getValuesSorted().get(size()-1);
    }

    public double sdp() {
        return Math.sqrt(varp());
    }

    public double sds() {
        return Math.sqrt(vars());
    }

    public double ss() {
        double ret = 0.0;
        for (Long value : new ArrayList<>(values())) {
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
        List<Long> ll = getValuesSorted();
        if (ll.size()==0) return 0;
        return ((ll.size()%2)==0) ? (ll.get(ll.size()/2) + ll.get((ll.size()/2)-1)) / 2 : ll.get(ll.size()/2);
    }

    public long mode() {
        List<Long> ll = getValuesSorted();
        if (ll.size()==0) return 0;
        int top = 0;
        long mode = 0L;
        for (int i = 0; i < ll.size(); i++) {
            int count = 0;
            for (int j = 0; j < ll.size(); j++) {
                if (ll.get(j) == ll.get(i)) count++;
            }
            if (count > top) {
                top = count;
                mode = ll.get(i);
            }
        }
        return mode;
    }

    public long percentile(final int p) {
        List<Long> ll = getValuesSorted();
        if (ll.size()==1) return ll.get(0);
        int pos = p * (ll.size() + 1) / 100;
        if (pos < 1) return ll.get(0);
        if (pos > ll.size()) return ll.get(ll.size()-1);
        return ll.get(pos);
    }

    public LastHashLongs copy() {
        return new LastHashLongs(this);
    }

    public void sortByValue() {
        List< Map.Entry<String, Long> > list = new LinkedList<>(entrySet());
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
        clear();
        for(Map.Entry<String, Long> map : list){
            put(map.getKey(), map.getValue());
        }
    }

    public void sortByKey() {
        List< Map.Entry<String, Long> > list = new LinkedList<>(entrySet());
        Collections.sort(list, Comparator.comparing(Map.Entry::getKey));
        clear();
        for(Map.Entry<String, Long> map : list){
            put(map.getKey(), map.getValue());
        }
    }

    public List<Long> getValuesSorted() {
        List<Long> l = new ArrayList<>(values());
        Collections.sort(l);
        return l;
    }

    public List<String> getKeysSorted() {
        List<String> l = new ArrayList<>(keySet());
        Collections.sort(l);
        return l;
    }

    public LastHashLongs copySortedByValues() {
        LastHashLongs ll = copy();
        ll.sortByValue();
        return ll;
    }

}
