package vn.kltn.KLTN.configuration;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.MetadataField;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import redis.clients.jedis.JedisPooled;

@Configuration
public class AIConfiguration {
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
}
