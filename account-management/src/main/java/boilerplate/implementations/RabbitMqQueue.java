package boilerplate.implementations;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import boilerplate.Event;
import boilerplate.MessageQueue;
import micro.events.AccountCreated;
import micro.events.TokenAdded;

public class RabbitMqQueue implements MessageQueue {

	private static final String DEFAULT_HOSTNAME = "localhost";
	private static final String EXCHANGE_NAME = "eventsExchange";
	private static final String QUEUE_TYPE = "topic";

	private Channel channel;
	private String hostname;

	public RabbitMqQueue() {
		this(DEFAULT_HOSTNAME);
	}

	public RabbitMqQueue(String hostname) {
		this.hostname = hostname;
		channel = setUpChannel();
	}

	@Override
	public void publish(Event event) {
		String message = new Gson().toJson(event);
		try {
			System.out.println("[X]: " + message);
			channel.basicPublish(EXCHANGE_NAME, event.getType(), null, message.getBytes("UTF-8"));
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	private Channel setUpChannel() {
		Channel chan;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(hostname);
			Connection connection = factory.newConnection();
			chan = connection.createChannel();
			chan.exchangeDeclare(EXCHANGE_NAME, QUEUE_TYPE);
		} catch (IOException | TimeoutException e) {
			throw new Error(e);
		}
		return chan;
	}

	@Override
	public void addHandler(String topic, Consumer<Event> handler) {
		var chan = setUpChannel();
		try {
			String queueName = chan.queueDeclare().getQueue();
			chan.queueBind(queueName, EXCHANGE_NAME, topic);

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");

				Event event;
				if (topic.equals("AccountRegistered")) {
					event = new Gson().fromJson(message, AccountCreated.class);
				} 
				else if (topic.equals("TokenAdded")) {
					event = new Gson().fromJson(message, TokenAdded.class);
				}
				else {
					event = new Gson().fromJson(message, Event.class);
				}
				handler.accept(event);
			};
			chan.basicConsume(queueName, true, deliverCallback, consumerTag -> {
			});
		} catch (IOException e1) {
			throw new Error(e1);
		}
	}
}