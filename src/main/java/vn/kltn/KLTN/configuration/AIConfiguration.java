package vn.kltn.KLTN.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.MetadataField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import redis.clients.jedis.JedisPooled;

@Configuration
public class AIConfiguration {
	@Value("classpath:/GuideForAI.txt")
	private Resource resource;

	@Bean
	public JedisPooled createJedisPooled() {
		return new JedisPooled("redis-12521.crce194.ap-seast-1-1.ec2.cloud.redislabs.com", 12521, "root",
				"Nguyentritin@123");
	}

	@Bean
	@Primary
	public VectorStore createVectorStore(EmbeddingModel embeddingModel, JedisPooled pool) {
		return RedisVectorStore.builder(pool, embeddingModel).indexName("product")
				.metadataFields(MetadataField.text("name"), MetadataField.text("category"), MetadataField.text("image"),
						MetadataField.text("productStatus"), MetadataField.numeric("price"))
				.initializeSchema(true).build();
	}

	@Bean
	public ChatClient createChatClient(ChatClient.Builder builder) {
		ChatMemory memory = MessageWindowChatMemory.builder().maxMessages(5).build(); // lưu tối đa 5 tin nhắn vào bộ
																						// nhớ
		return builder.defaultSystem(resource).defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
				.build();
	}
}
