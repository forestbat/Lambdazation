package org.lambdazation.common.core;

import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.lambdazation.Lambdazation;
import org.lambdazation.common.entity.*;

public final class LambdazationEntityTypes {
	public final Lambdazation lambdazation;

	public final EntityType<JavaEntity> entityTypeJava;
	public final EntityType<CSharpEntity> entityTypeCSharp;
	public final EntityType<JavaObjectEntity> entityTypeJavaObject;
	public final EntityType<CSharpEntityPart> entityTypeCSharpPart;
	public final EntityType<CSharpObjectEntity> entityTypeCSharpObject;

	public LambdazationEntityTypes(Lambdazation lambdazation) {
		this.lambdazation = lambdazation;
		entityTypeJava = EntityType.Builder.create(
			(EntityType<JavaEntity> entityTypeJava, World world) -> new JavaEntity(lambdazation, entityTypeJava,
				world),
			EntityCategory.MONSTER).build("lambdazation:java");
		Registry.register(Registry.ENTITY_TYPE, "lambdazation:entity_java", entityTypeJava);
		entityTypeCSharp = EntityType.Builder.create
			((EntityType<CSharpEntity> entityTypeCSharp, World world) -> new CSharpEntity(lambdazation,
					entityTypeCSharp, world),
				EntityCategory.MONSTER)
			.build("lambdazation:csharp");
		Registry.register(Registry.ENTITY_TYPE, "lambdazation:entity_csharp", entityTypeCSharp);
		entityTypeJavaObject = EntityType.Builder.create(
			(EntityType<JavaObjectEntity> entityTypeJavaObject, World world) -> new JavaObjectEntity(lambdazation,
				entityTypeJavaObject, world),
			EntityCategory.MONSTER)
			.build("lambdazation:java_object");
		Registry.register(Registry.ENTITY_TYPE, "lambdazation:entity_java_object", entityTypeJavaObject);
		entityTypeCSharpPart = EntityType.Builder.create(
			(EntityType<CSharpEntityPart> entityTypeCSharpPart, World world) ->
				new CSharpEntityPart(lambdazation,entityTypeCSharpPart, world,0,0), EntityCategory.MONSTER).
			build("lambdazation:csharp_part");
		Registry.register(Registry.ENTITY_TYPE, "lambdazation:entity_csharp_part", entityTypeCSharpPart);
		entityTypeCSharpObject = EntityType.Builder.create(
			(EntityType<CSharpObjectEntity> entityTypeCSharpObject, World world) -> new CSharpObjectEntity
				(lambdazation,entityTypeCSharpObject,world), EntityCategory.MONSTER).build("lambdazation:csharp_object");
		Registry.register(Registry.ENTITY_TYPE, "lambdazation:entity_csharp_object", entityTypeCSharpObject);
	}
}