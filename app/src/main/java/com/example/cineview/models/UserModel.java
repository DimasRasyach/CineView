    package com.example.cineview.models;

    import com.google.gson.annotations.SerializedName;

    import java.util.Date;
    import java.util.List;

    public class UserModel {
        @SerializedName("_id")
        private String id;
        @SerializedName("username")
        private String username;
        @SerializedName("email")
        private String email;
        @SerializedName("createdAt")
        private Date createdAt;
        @SerializedName("favoriteMovies")
        private List<String> favoriteMovies;

        public UserModel(String id, String username, String email, Date createdAt, List<String> favoriteMovies) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.createdAt = createdAt;
            this.favoriteMovies = favoriteMovies;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public List<String> getFavoriteMovies() {
            return favoriteMovies;
        }

        public void setFavoriteMovies(List<String> favoriteMovies) {
            this.favoriteMovies = favoriteMovies;
        }
    }
