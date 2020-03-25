package com.talkdesk.tdx.nlp.transcription;

import com.talkdesk.tdx.nlp.transcription.serialization.TranscriptionSerde;
import com.talkdesk.tdx.nlp.transcription.serialization.TranscriptionStateSerde;
import java.util.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology.AutoOffsetReset;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class JoinStreamAndTable {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "join");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.73:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 500);

        Serde<Transcription> transcriptionSerde = new TranscriptionSerde();
        Serde<TranscriptionState> transcriptionStateSerde = new TranscriptionStateSerde();
        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Transcription> transcriptionStream = builder.stream("transcriptions",
            Consumed.with(stringSerde, transcriptionSerde));

        KTable<String, TranscriptionState> transcriptionStateTable = builder.table("transcription-states",
            Consumed.with(stringSerde, transcriptionStateSerde).withOffsetResetPolicy(AutoOffsetReset.LATEST));

        ValueJoiner<Transcription, TranscriptionState, String> joiner = new TranscriptionStateJoiner();

        KStream<String, String> joinedStream = transcriptionStream.join(
            transcriptionStateTable, joiner);

        joinedStream.peek((key, value) -> System.out.println(value));

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), props);
        kafkaStreams.start();
        Thread.sleep(30000);
        System.err.println("Shutting down");
        kafkaStreams.close();
    }
}
