package com.hexagram2021.fiahi.client.model;

import com.hexagram2021.fiahi.common.handler.ItemStackFoodHandler;
import com.hexagram2021.fiahi.common.item.capability.IFrozenRottenFood;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public record FIAHIBakedModel(BakedModel original, BakedModel frozen1, BakedModel frozen2, BakedModel frozen3, BakedModel rotten1, BakedModel rotten2, BakedModel rotten3) implements BakedModel {
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, Random random) {
		return this.original.getQuads(state, direction, random);
	}
	
	@Override
	public boolean useAmbientOcclusion() {
		return this.original.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return this.original.isGui3d();
	}

	@Override
	public boolean usesBlockLight() {
		return this.original.usesBlockLight();
	}

	@Override
	public boolean isCustomRenderer() {
		return this.original.isCustomRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return this.original.getParticleIcon();
	}

	@Override
	public ItemOverrides getOverrides() {
		return new ItemOverrides() {
			@Override @Nullable
			public BakedModel resolve(BakedModel bakedModel, ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed) {
				BakedModel ret = FIAHIBakedModel.this.getTemperatureEffectBakedModel(itemStack);
				return ret.getOverrides().resolve(ret, itemStack, clientLevel, livingEntity, seed);
			}
		};
	}

	@Override
	public ItemTransforms getTransforms() {
		return FIAHIBakedModel.this.original.getTransforms();
	}

	private BakedModel getTemperatureEffectBakedModel(ItemStack itemStack) {
		if(!IFrozenRottenFood.canBeFrozenRotten(itemStack)) {
			return this.original;
		}
		CompoundTag nbt = itemStack.getTag();
		if(nbt == null || !nbt.contains(ItemStackFoodHandler.FIAHI_TAG_TEMPERATURE, Tag.TAG_ANY_NUMERIC)) {
			return this.original;
		}
		int temp = (int)nbt.getDouble(ItemStackFoodHandler.FIAHI_TAG_TEMPERATURE);
		int frozenLevel = IFrozenRottenFood.getFrozenLevel(temp);
		int rottenLevel = IFrozenRottenFood.getRottenLevel(temp);
		if(frozenLevel > 0) {
			return switch (frozenLevel) {
				case 1 -> this.frozen1;
				case 2 -> this.frozen2;
				case 3 -> this.frozen3;
				default -> this.original;
			};
		}
		if(rottenLevel > 0) {
			return switch (rottenLevel) {
				case 1 -> this.rotten1;
				case 2 -> this.rotten2;
				case 3 -> this.rotten3;
				default -> this.original;
			};
		}
		return this.original;
	}

	//Forge
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand, IModelData extraData) {
		return this.original.getQuads(state, side, rand, extraData);
	}

	@Override
	public boolean useAmbientOcclusion(BlockState state) {
		return this.original.useAmbientOcclusion(state);
	}

	@Override
	public boolean doesHandlePerspectives() {
		return this.original.doesHandlePerspectives();
	}

	@Override
	public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack) {
		return this.original.handlePerspective(cameraTransformType, poseStack);
	}

	@Override
	public IModelData getModelData(BlockAndTintGetter level, BlockPos pos, BlockState state, IModelData modelData) {
		return this.original.getModelData(level, pos, state, modelData);
	}

	@Override
	public TextureAtlasSprite getParticleIcon(IModelData data) {
		return this.original.getParticleIcon(data);
	}

	@Override
	public boolean isLayered() {
		return this.original.isLayered();
	}

	@Override
	public List<Pair<BakedModel, RenderType>> getLayerModels(ItemStack itemStack, boolean fabulous) {
		return this.original.getLayerModels(itemStack, fabulous);
	}
}
