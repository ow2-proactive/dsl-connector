package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rendering model for entities
 */
@Getter
@EqualsAndHashCode
public class EntitiesRendering {

    private final Map<String,EntityRendering> entities;

    private EntitiesRendering(Map<String,EntityRendering> entities) {
        this.entities = entities;
    }

    public static class Builder{
        private final Map<String,EntityRendering> entities;

        public Builder(){
            entities = new HashMap<>();
        }

        public Builder addEntity(EntityRendering entity){
            this.entities.put(entity.getId(),entity);
            return this;
        }

        public Builder addEntities(List<EntityRendering> results) {
            results.stream().forEach( entityRendering -> this.addEntity(entityRendering));
            return this;
        }

        public EntitiesRendering build(){
            return new EntitiesRendering(entities);
        }

    }
}
