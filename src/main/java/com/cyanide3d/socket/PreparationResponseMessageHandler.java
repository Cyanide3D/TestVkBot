package com.cyanide3d.socket;

import com.vk.api.sdk.objects.base.Image;
import com.vk.api.sdk.objects.base.StickerAnimation;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachment;
import com.vk.api.sdk.objects.photos.PhotoSizes;
import com.vk.api.sdk.objects.wall.Wallpost;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PreparationResponseMessageHandler {


    public String photo(MessageAttachment attachment) {
        List<PhotoSizes> photo = attachment.getPhoto().getSizes();
        return photo.get(photo.size() - 1).getUrl().toString();
    }

    public String video(MessageAttachment attachment) {
        return "https://vk.com/video?z=video" + attachment.getVideo().getOwnerId() + "_" + attachment.getVideo().getId();
    }

    public String sticker(MessageAttachment attachment){
        List<Image> staticSticker = attachment.getSticker().getImages();
        List<StickerAnimation> animationSticker = attachment.getSticker().getAnimations();
        if (animationSticker != null){
            return animationSticker.get(0).getUrl().toString();
        }
        return staticSticker.get(0).getUrl().toString();
    }

    public String doc(MessageAttachment attachment) {
        return attachment.getDoc().getUrl().toString();
    }

    public String link(MessageAttachment attachment) {
        return attachment.getLink().getUrl().toString();
    }

    public String audio(MessageAttachment attachment) {
        return "Аудиофайл: " + attachment.getAudio().getArtist() + " - " + attachment.getAudio().getTitle();
    }

    public String audioMessage(MessageAttachment attachment) {
        return "Аудиосообщение: " + attachment.getAudioMessage().getLinkMp3().toString();
    }

    public String wall(Message message) {
        List<MessageAttachment> attachments = message.getAttachments();
        StringBuilder result = new StringBuilder()
                .append("**ЗАПИСЬ СО СТЕНЫ**\n")
                .append("Содержание:\n");
        if (attachments != null) {
            if (attachments.get(0).getWall().getCopyHistory() != null) {
                result.append(makeHistoryAttachments(attachments));
            } else {
                result.append(makeSelfAttachments(attachments));
            }
        }
        return result.toString();
    }

    private String makeSelfAttachments(List<MessageAttachment> attachments) {
        StringBuilder result = new StringBuilder();
        for (MessageAttachment attachment : attachments) {
            List<WallpostAttachment> wall = attachment.getWall().getAttachments();
            if (wall == null) {
                result.append(attachment.getWall().getText());
                break;
            }
            result.append(analyzeWallAttachments(wall));
        }
        return result.toString();
    }

    private String makeHistoryAttachments(List<MessageAttachment> attachments) {
        StringBuilder result = new StringBuilder();
        for (MessageAttachment attachment : attachments) {
            for (Wallpost wallpost : attachment.getWall().getCopyHistory()) {
                if (!StringUtils.isEmpty(wallpost.getText())) {
                    result.append(wallpost.getText()).append("\n");
                }
                result.append(analyzeWallAttachments(wallpost.getAttachments()));
            }
        }
        return result.toString();
    }

    private String analyzeWallAttachments(List<WallpostAttachment> attachments) {
        StringBuilder result = new StringBuilder();
        for (WallpostAttachment wallpostAttachment : attachments) {
            String type = wallpostAttachment.getType().getValue();
            if (type.equals("video")) {
                result.append("https://vk.com/video?z=video").append(wallpostAttachment.getVideo().getOwnerId()).append("_").append(wallpostAttachment.getVideo().getId());
            } else if (type.equals("photo")) {
                List<PhotoSizes> sizes = wallpostAttachment.getPhoto().getSizes();
                result.append(sizes.get(sizes.size() - 1).getUrl());
            } else if (type.equals("audio")) {
                result.append("Аудио: ").append(wallpostAttachment.getAudio().getArtist()).append(" - ").append(wallpostAttachment.getAudio().getTitle());
            } else if (type.equals("doc")) {
                result.append(wallpostAttachment.getDoc().getUrl());
            } else if (type.equals("link")) {
                result.append(wallpostAttachment.getLink().getUrl());
            } else {
                System.out.println(type);
                result.append("UNKNOWN");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
