package micro;

import boilerplate.implementations.RabbitMqQueue;
import micro.adapters.EventPublisher;
import micro.adapters.RabbitMqEventPublisher;
import micro.adapters.RabbitMqFacade;
import micro.repositories.PaymentReadModelRepository;
import micro.repositories.PaymentRepository;
import micro.service.*;

public class StartUp {
	private static RabbitMqFacade facade;
	public static void main(String[] args) throws Exception {
		new StartUp().startUp();
	}

	private void startUp() throws Exception {
		System.out.println("STARTING the Payment Management Service");
		Thread.sleep(10000);
		var mq = new RabbitMqQueue("rabbitMq");
		PaymentRepository repo = new PaymentRepository(mq);
		PaymentReadModelRepository rm = new PaymentReadModelRepository(mq);
		EventPublisher pub = new RabbitMqEventPublisher(mq);

		var service = new PaymentManagementService(repo, rm, pub);
		facade = new RabbitMqFacade(mq, service);
		Thread.currentThread().join();
	}
}