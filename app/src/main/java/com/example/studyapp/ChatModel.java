package com.example.studyapp;

public class ChatModel {
        public static final int TYPE_USER = 0;
        public static final int TYPE_GEMINI = 1;

        private String text;
        private int type;

        public ChatModel(String text, int type) {
            this.text = text;
            this.type = type;
        }

        public String getText() {
            return text;
        }
        public int getType() {
            return type;
        }


}
