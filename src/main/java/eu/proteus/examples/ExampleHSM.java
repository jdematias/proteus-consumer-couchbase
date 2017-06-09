package eu.proteus.examples;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import eu.proteus.consumer.model.Measurement;
import eu.proteus.consumer.serialization.ProteusSerializer;

public class ExampleHSM {

	public static void main(String[] args) {

		ArrayList<String> topicsList = new ArrayList<String>();

		HashMap<String, Object> kafkaProperties = new HashMap<String, Object>();

		topicsList.add("proteus-flatness");
		kafkaProperties.put("bootstrap.servers", "192.168.4.246:6667,192.168.4.247:6667,192.168.4.248:6667");
		kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
		kafkaProperties.put("value.deserializer", ProteusSerializer.class.getName());
		kafkaProperties.put("group.id", "proteus");

		KafkaConsumer<Integer, Measurement> kafkaConsumer;

		ProteusSerializer myValueDeserializer = new ProteusSerializer();
		IntegerDeserializer keyDeserializer = new IntegerDeserializer();
		kafkaConsumer = new KafkaConsumer<Integer, Measurement>(kafkaProperties, keyDeserializer, myValueDeserializer);
		kafkaConsumer.subscribe(topicsList);

		try {
			while (true) {
				ConsumerRecords<Integer, Measurement> records = kafkaConsumer.poll(1);
				for (ConsumerRecord<Integer, Measurement> record : records) {
					System.out.println("traza");
					System.out.println(record);
				}

			}
		} finally {
			kafkaConsumer.close();
		}

	}

}
