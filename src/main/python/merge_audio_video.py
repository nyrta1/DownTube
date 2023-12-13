import sys
from numba import jit
from moviepy.editor import VideoFileClip, AudioFileClip

def merge_audio_video(audio_path, video_path, output_path):
    try:
        video_clip = VideoFileClip(video_path)
        audio_clip = AudioFileClip(audio_path)
        video_clip_with_audio = video_clip.set_audio(audio_clip)
        video_clip_with_audio.write_videofile(output_path, codec="libx264", audio_codec="aac")
        return True
    except Exception as e:
        print("Error:", e)
        return False

@jit
def merge_with_optimization(audio_path, video_path, output_path):
    return merge_audio_video(audio_path, video_path, output_path)

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Usage: python merge_audio_video.py audio_path video_path output_path")
        sys.exit(1)

    audio_path = sys.argv[1]
    video_path = sys.argv[2]
    output_path = sys.argv[3]

    success = merge_with_optimization(audio_path, video_path, output_path)
    sys.exit(0 if success else 1)
