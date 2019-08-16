// Generated by delombok at Sun Mar 24 19:34:08 CDT 2019
package com.eden.orchid.api.generators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.LongConsumer;

import static com.eden.orchid.utilities.OrchidExtensionsKt.makeMillisReadable;

public class GeneratorMetrics {
    private String key;
    private long indexingStartTime;
    private long generatingStartTime;
    private long indexingEndTime;
    private long generatingEndTime;
    private List<Long> pageGenerationTimes;

    public GeneratorMetrics(String key) {
        this.key = key;
        indexingStartTime = Long.MAX_VALUE;
        indexingEndTime = Long.MIN_VALUE;
        generatingStartTime = Long.MAX_VALUE;
        generatingEndTime = Long.MIN_VALUE;
        pageGenerationTimes = new ArrayList<>();
    }

    void startIndexing() {
        indexingStartTime = System.currentTimeMillis();
    }

    void startGenerating() {
        generatingStartTime = System.currentTimeMillis();
    }

    void stopIndexing() {
        indexingEndTime = System.currentTimeMillis();
    }

    void stopGenerating() {
        generatingEndTime = System.currentTimeMillis();
    }

    void addPageGenerationTime(long millis) {
        pageGenerationTimes.add(millis);
    }

// Get formatted values
//----------------------------------------------------------------------------------------------------------------------

    String getIndexingTime() {
        return makeMillisReadable(indexingEndTime - indexingStartTime);
    }

    String getGeneratingTime() {
        return makeMillisReadable(generatingEndTime - generatingStartTime);
    }

    String getTotalTime() {
        return makeMillisReadable(generatingEndTime - indexingStartTime);
    }

    String getMeanPageTime() {
        if (pageGenerationTimes.size() > 0) {
            return makeMillisReadable(pageGenerationTimes.stream().collect(Averager::new, Averager::accept, Averager::combine).average());
        } else {
            return "N/A";
        }
    }

    String getMedianPageTime() {
        if (pageGenerationTimes.size() > 0) {
            pageGenerationTimes.sort(Comparator.naturalOrder());
            return makeMillisReadable(pageGenerationTimes.get((int) Math.floor(pageGenerationTimes.size() / 2)));
        } else {
            return "N/A";
        }
    }

    void compose(GeneratorMetrics metric) {
        indexingStartTime = Math.min(indexingStartTime, metric.indexingStartTime);
        indexingEndTime = Math.max(indexingEndTime, metric.indexingEndTime);
        generatingStartTime = Math.min(generatingStartTime, metric.generatingStartTime);
        generatingEndTime = Math.max(generatingEndTime, metric.generatingEndTime);
        pageGenerationTimes.addAll(metric.getPageGenerationTimes());
    }

    int getPageCount() {
        return getPageGenerationTimes().size();
    }


    static class Averager implements LongConsumer {
        private long total = 0;
        private long count = 0;

        public double average() {
            return count > 0 ? ((double) total) / count : 0;
        }

        public void accept(long i) {
            total += i;
            count++;
        }

        public void combine(Averager other) {
            total += other.total;
            count += other.count;
        }
    }

    public String getKey() {
        return this.key;
    }

    public long getIndexingStartTime() {
        return this.indexingStartTime;
    }

    public long getGeneratingStartTime() {
        return this.generatingStartTime;
    }

    public long getIndexingEndTime() {
        return this.indexingEndTime;
    }

    public long getGeneratingEndTime() {
        return this.generatingEndTime;
    }

    public List<Long> getPageGenerationTimes() {
        return this.pageGenerationTimes;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setIndexingStartTime(final long indexingStartTime) {
        this.indexingStartTime = indexingStartTime;
    }

    public void setGeneratingStartTime(final long generatingStartTime) {
        this.generatingStartTime = generatingStartTime;
    }

    public void setIndexingEndTime(final long indexingEndTime) {
        this.indexingEndTime = indexingEndTime;
    }

    public void setGeneratingEndTime(final long generatingEndTime) {
        this.generatingEndTime = generatingEndTime;
    }

    public void setPageGenerationTimes(final List<Long> pageGenerationTimes) {
        this.pageGenerationTimes = pageGenerationTimes;
    }
}