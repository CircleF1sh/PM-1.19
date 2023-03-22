package com.fish.pm.client.models;

import com.fish.pm.PM;
import com.fish.pm.common.entities.DwarfCrocodile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Map;

public class DwarfCrocodileModel <T extends DwarfCrocodile> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("pm", "dwarf_crocodile"), PM.MOD_ID);
    private final ModelPart Head;
    private final ModelPart Torso;
    private final ModelPart L_front_leg;
    private final ModelPart R_front_leg;
    private final ModelPart L_back_leg;
    private final ModelPart R_back_leg;
    private final ModelPart bone;
    private final ModelPart bone2;

    // Constants for the walking cycle animation
    private static final float LEG_SWING_PERIOD = (float) Math.PI * 0.5f;
    private static final float LEG_SWING_AMPLITUDE = 0.3f;
    private static final float BODY_MOVE_PERIOD = (float) Math.PI * 2f;
    private static final float BODY_MOVE_AMPLITUDE = 0.1f;

    // Fields for the walking cycle animation
    private float frontLeftLegAngle;
    private float frontRightLegAngle;
    private float backLeftLegAngle;
    private float backRightLegAngle;
    private float bodyMoveAngle;

    public DwarfCrocodileModel(ModelPart root) {
        this.Head = root.getChild("Head");
        this.Torso = root.getChild("Torso");
        this.L_front_leg = root.getChild("L_front_leg");
        this.R_front_leg = root.getChild("R_front_leg");
        this.L_back_leg = root.getChild("L_back_leg");
        this.R_back_leg = root.getChild("R_back_leg");
        this.bone = root.getChild("bone");
        this.bone2 = bone.getChild("bone2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 15).addBox(-3.0F, -1.5F, -9.0F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(21, 0).addBox(-3.0F, -2.5F, -5.0F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.5F, -5.0F));

        PartDefinition Torso = partdefinition.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition L_front_leg = partdefinition.addOrReplaceChild("L_front_leg", CubeListBuilder.create().texOffs(17, 4).mirror().addBox(-2.0F, -0.05F, -3.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 24.0F, -3.0F));

        PartDefinition R_front_leg = partdefinition.addOrReplaceChild("R_front_leg", CubeListBuilder.create().texOffs(17, 4).addBox(-1.0F, -0.05F, -3.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 24.0F, -3.0F));

        PartDefinition L_back_leg = partdefinition.addOrReplaceChild("L_back_leg", CubeListBuilder.create().texOffs(21, 15).mirror().addBox(-2.0F, -2.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-3.0F, -0.05F, -4.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 24.0F, 3.0F));

        PartDefinition R_back_leg = partdefinition.addOrReplaceChild("R_back_leg", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.05F, -4.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(21, 15).addBox(-1.0F, -2.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(3.0F, 24.0F, 3.0F));

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(2, 19).addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 4.0F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(29, 20).addBox(0.0F, -2.0F, 0.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        L_front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        R_front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        L_back_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        R_back_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInWaterOrBubble()) {
                float speed = 3.0f;
                float degree = 1.0f;

                this.Torso.xRot = Mth.cos(limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount;
                this.Head.xRot = Mth.cos(0.25F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount;
                this.bone.xRot = Mth.cos(0.25F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount;
            this.saveAnimationValues(entity);

        } else {

            float limbSwingProgress = limbSwing * 2f;
            this.frontLeftLegAngle = Mth.cos(limbSwingProgress + (float) Math.PI) * limbSwingAmount * LEG_SWING_AMPLITUDE;
            this.frontRightLegAngle = Mth.cos(limbSwingProgress) * limbSwingAmount * LEG_SWING_AMPLITUDE;
            this.backLeftLegAngle = Mth.cos(limbSwingProgress) * limbSwingAmount * LEG_SWING_AMPLITUDE;
            this.backRightLegAngle = Mth.cos(limbSwingProgress + (float) Math.PI) * limbSwingAmount * LEG_SWING_AMPLITUDE;
            this.bodyMoveAngle = Mth.cos(ageInTicks * 0.05f) * BODY_MOVE_AMPLITUDE;

            // Set the model's limb positions and body position/rotation
            this.L_front_leg.xRot = this.frontLeftLegAngle;
            this.R_front_leg.xRot = this.frontRightLegAngle;
            this.L_back_leg.xRot = this.backLeftLegAngle;
            this.R_back_leg.xRot = this.backRightLegAngle;
            this.Torso.z = this.bodyMoveAngle;
            this.bone.yRot = this.bodyMoveAngle;
            this.bone2.yRot = this.bodyMoveAngle;


            this.saveAnimationValues(entity);
        }
    }


    private void saveAnimationValues(T entity) {
        Map<String, Vector3f> map = entity.getModelRotationValues();
        map.put("Torso", this.getRotationVector(this.Torso));
        map.put("Head", this.getRotationVector(this.Head));
        map.put("R_back_leg", this.getRotationVector(this.R_back_leg));
        map.put("L_back_leg", this.getRotationVector(this.L_back_leg));
        map.put("R_front_leg", this.getRotationVector(this.R_front_leg));
        map.put("L_front_leg", this.getRotationVector(this.L_front_leg));
        map.put("bone", this.getRotationVector(this.bone));
    }


    private Vector3f getRotationVector(ModelPart p_170402_) {
        return new Vector3f(p_170402_.xRot, p_170402_.yRot, p_170402_.zRot);
    }
}