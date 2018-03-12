package com.michszym.vehicles;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Value("${region}")
    private String region;

    @Value("${dynamodb.table.prefix}")
    private String prefix;

    @Value("${aws.profile:#{null}}")
    private String profile;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        if (StringUtils.isBlank(profile)) {
            return AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(new InstanceProfileCredentialsProvider(false))
                    .withRegion(region)
                    .build();
        } else {
            return AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider(profile))
                    .withRegion(region)
                    .build();
        }
    }

    @Bean
    public DynamoDBMapper dynamoDbMapper(AmazonDynamoDB amazonDynamoDB) {
        final DynamoDBMapperConfig.TableNameOverride tableNamePrefix = DynamoDBMapperConfig.TableNameOverride
                .withTableNamePrefix(prefix);
        final DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withTableNameOverride(tableNamePrefix)
                .withConversionSchema(ConversionSchemas.V2)
                .build();
        return new DynamoDBMapper(amazonDynamoDB, config);
    }
}
