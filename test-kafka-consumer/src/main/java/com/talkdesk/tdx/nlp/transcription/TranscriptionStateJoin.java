package com.talkdesk.tdx.nlp.transcription;

import javax.enterprise.context.*;
import javax.enterprise.inject.Produces;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.Topology.AutoOffsetReset;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueJoiner;

@ApplicationScoped
public class TranscriptionStateJoin {

    @Produces
    public Topology buildTopology() {
        Serde<Transcription> transcriptionSerde = new TranscriptionSerde();
        Serde<TranscriptionState> transcriptionStateSerde = new TranscriptionStateSerde();
        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Transcription> transcriptionStream = builder.stream("transcriptions",
            Consumed.with(stringSerde, transcriptionSerde));

        KTable<String, TranscriptionState> transcriptionStateTable = builder.table("transcription-states",
            Consumed.with(stringSerde, transcriptionStateSerde).withOffsetResetPolicy(AutoOffsetReset.LATEST));

        ValueJoiner<Transcription, TranscriptionState, TranscriptionState> joiner = new TranscriptionStateJoiner();

        KStream<String, TranscriptionState> joinedStream = transcriptionStream.join(
            transcriptionStateTable, joiner);

        joinedStream.to("transcription-states", Produced.with(stringSerde, transcriptionStateSerde));

        return builder.build();
    }
}
