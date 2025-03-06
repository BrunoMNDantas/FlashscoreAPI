package com.github.brunomndantas.flashscore.api.dataAccess.s3Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.DuplicatedEntityException;
import com.github.brunomndantas.repository4j.exception.NonExistentEntityException;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.Collection;
import java.util.function.Function;

public class S3Repository<K, E> implements IRepository<K, E> {

    private static final ObjectMapper MAPPER = new ObjectMapper();


    protected final S3Client s3Client;
    protected final String bucketName;
    protected final String directory;
    protected final Class<E> entityType;
    protected Function<E,K> keyExtractor;


    public S3Repository(
            S3Client s3Client, String bucketName,
            String directory, Class<E> entityType,
            Function<E,K> keyExtractor) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.directory = directory;
        this.entityType = entityType;
        this.keyExtractor = keyExtractor;
    }

    @Override
    public Collection<E> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(K key) throws RepositoryException {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(getS3Key(key))
                    .build();
            ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);

            return MAPPER.readValue(response.readAllBytes(), entityType);
        } catch (S3Exception e) {
            if(e.statusCode() == 404) {
                return null;
            }

            throw new RepositoryException("Error getting entity with key: " + key, e);
        } catch (Exception e) {
            throw new RepositoryException("Error getting entity with key: " + key, e);
        }
    }

    @Override
    public void insert(E entity) throws RepositoryException {
        try {
            K key = keyExtractor.apply(entity);

            if(get(key) != null) {
                throw new DuplicatedEntityException("There is already an entity with key:" + key);
            }

            String json = new ObjectMapper().writeValueAsString(entity);
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(getS3Key(key))
                    .build();
            s3Client.putObject(request, RequestBody.fromString(json));
        } catch (DuplicatedEntityException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("Error inserting entity", e);
        }
    }

    @Override
    public void update(E entity) throws RepositoryException {
        try {
            K key = keyExtractor.apply(entity);

            if(get(key) == null) {
                throw new NonExistentEntityException("There is no entity with key:" + key);
            }

            String json = new ObjectMapper().writeValueAsString(entity);
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(getS3Key(key))
                    .build();
            s3Client.putObject(request, RequestBody.fromString(json));
        } catch (NonExistentEntityException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("Error updating entity", e);
        }
    }

    @Override
    public void delete(K key) throws RepositoryException {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(getS3Key(key))
                    .build();
            s3Client.deleteObject(request);
        } catch (Exception e) {
            throw new RepositoryException("Error deleting entity with key: " + key, e);
        }
    }

    protected String getS3Key(K key) {
        return directory + "/" + key;
    }

}