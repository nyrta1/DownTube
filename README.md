# DownTube

DownTube is a high-speed YouTube video downloader that offers high-quality media files. It enables users to download individual videos, playlists, and entire channels. The tool supports various video qualities such as 4K, 2K, FullHD, HD, 360p, 240p, and 144p. Additionally, it provides options to download audio in high, medium, or low quality.

## Technologies Used
- Angular 16
- Spring Boot
- Docker
- TypeScript
- HTML5
- FFMPEG
- Docker Compose
- JSON Data
- PostgreSQL
- Spring MVC
- CryptoJS
- JWT Authentication
- JPA Hibernate
- Bootstrap 5

## Installation
To install, use Docker Desktop application. In the command line interface (CLI), execute `docker-compose up` to download the necessary libraries and dependencies via Dockerfiles.

## Usage
Access the Spring Boot backend at `localhost:8080` and the Angular 16 app at `localhost:4200`.

## Explanation of the code

### RESTful API Controllers

#### Authentication Controller
This Java Spring controller handles user authentication operations such as registration and login to the system. It utilizes JWT (JSON Web Token) for secure authentication.

**Endpoints:**
- `POST /register`: Registers a user to the system. Request Body: LoginRequest object containing username and password. Response: Returns a message indicating successful registration or an error message if registration fails.
- `POST /login`: Logs a user into the system. Request Body: LoginRequest object containing username and password. Response: Returns a JWT token upon successful authentication along with user details and roles, or an error message if authentication fails.
- `POST /validate-jwt-token`: Validates a JWT token. PreAuthorized for 'ADMIN' role. Request Body: JwtResponse object containing the JWT token to be validated. Response: Returns a message confirming token validity or an error message if the token is invalid or expired.

#### File Downloader Controller
This Java Spring controller facilitates downloading media files from specified URLs with customizable quality and format options. It uses a MediaFileDownloader service for handling the download process.

**Endpoints:**
- `GET /media/download/video-with-audio`: Downloads a video file with audio. Query Parameters: URL, quality, format. Response: Returns the downloaded file as an attachment with specified filename.
- `GET /media/download/audio`: Downloads an audio file. Query Parameters: URL, quality, format. Response: Returns the downloaded file as an attachment with specified filename.
- `GET /media/download/video`: Downloads a video file. Query Parameters: URL, quality, format. Response: Returns the downloaded file as an attachment with specified filename.
- `GET /media/download/merged-audio-with-video`: Downloads a merged audio-video file. Query Parameters: URL, quality, format. Response: Returns the downloaded file as an attachment with specified filename.

#### JSON Controller
This Java Spring controller manages the extraction and retrieval of JSON data related to YouTube videos, playlists, and channels. It utilizes a JSONService for processing JSON data and a YouTubeLinkExtractor for extracting relevant IDs from YouTube URLs.

**Endpoints:**
- `GET /json/downloader/all`: Retrieves JSON data for a specific YouTube video. Query Parameter: videoUrl (URL of the YouTube video). Response: Returns JSON data related to the specified video.
- `GET /json/downloader/playlist`: Retrieves JSON data for a YouTube playlist. Query Parameter: playlistUrl (URL of the YouTube playlist). Response: Returns JSON data related to the specified playlist.
- `GET /json/downloader/channel`: Retrieves JSON data for a YouTube channel. Query Parameter: channelUrl (URL of the YouTube channel). Response: Returns JSON data related to the specified channel.

#### YouTube Link Extractor
This Java class provides methods to extract various identifiers (IDs) from YouTube video, playlist, and channel URLs.

**Methods:**
- `extractVideoId(String link)`: Extracts the video ID from a YouTube video URL. Parameters: YouTube video URL. Returns: Video ID extracted from the URL.
- `extractPlaylistId(String url)`: Extracts the playlist ID from a YouTube playlist URL. Parameters: YouTube playlist URL. Returns: Playlist ID extracted from the URL.
- `extractChannelId(String url)`: Extracts the channel ID from a YouTube channel URL. Parameters: YouTube channel URL. Returns: Channel ID extracted from the URL.

**Usage:** Call the appropriate method with the respective YouTube URL. Each method returns the extracted ID or null if no ID is found. Ensure the provided URLs are valid YouTube URLs. Integrate these methods into applications for processing YouTube content URLs and extracting IDs as needed.

### Entities

#### User Entity
This Java class defines the structure of a user entity for the application. It includes attributes such as username, password, and roles, along with relationships to downloaded media files.

**Attributes:**
- `id`: Unique identifier for the user.
- `username`: Username of the user (must be at least 8 characters long and unique).
- `password`: Password of the user (must be at least 8 characters long).
- `roles`: List of roles assigned to the user.
- `downloadedMedia`: List of media files downloaded by the user.

**Annotations:**
- `@Entity`: Specifies that the class is an entity, mapped to a database table.
- `@Table`: Specifies the name of the database table for the entity.
- `@Data`: Lombok annotation to generate getter, setter, equals, hashCode, and toString methods.
- `@NoArgsConstructor`: Lombok annotation to generate a no-argument constructor.
- `@AllArgsConstructor`: Lombok annotation to generate an all-argument constructor.
- `@Id`: Specifies the primary key of the entity.
- `@GeneratedValue`: Specifies the strategy for generating the primary key values.
- `@Column`: Specifies the mapping for a persistent entity attribute.
- `@CollectionTable`: Specifies the table that is used for the mapping of collections.
- `@ManyToMany`: Specifies a many-to-many relationship between entities.
- `@JoinTable`: Specifies the mapping of associations for a many-to-many relationship.

#### Media File Entity
This Java class defines the structure of a media file entity for the application. It includes attributes such as video ID, file path, creation timestamp, file quality, and file type.

**Attributes:**
- `id`: Unique identifier for the media file.
- `videoId`: ID of the associated video.
- `filePath`: Path of the media file.
- `createdAt`: Timestamp indicating the creation time of the media file.
- `fileQuality`: Quality of the media file.
- `fileType`: Type of the media file (e.g., video, audio).

**Annotations:**
- `@Entity`: Specifies that the class is an entity, mapped to a database table.
- `@Table`: Specifies the name of the database table for the entity.
- `@Data`: Lombok annotation to generate getter, setter, equals, hashCode, and toString methods.
- `@NoArgsConstructor`: Lombok annotation to generate a no-argument constructor.
- `@AllArgsConstructor`: Lombok annotation to generate an all-argument constructor.
- `@Id`: Specifies the primary key of the entity.
- `@GeneratedValue`: Specifies the strategy for generating the primary key values.
- `@Column`: Specifies the mapping for a persistent entity attribute.
- `@CreationTimestamp`: Specifies that the property value should be set to the current database server timestamp when the entity is inserted.

### JPA Repositories

#### UserRepository
This Java interface represents the repository for user entities in the application. It extends the JpaRepository interface provided by Spring Data JPA, providing CRUD (Create, Read, Update, Delete) operations for user entities.

**Methods:**
- `findAll()`: Retrieves all users from the database.
- `findByUsername(String username)`: Retrieves a user by their username.

#### MediaFileRepository
This Java interface represents the repository for media file entities in the application. It extends the JpaRepository interface provided by Spring Data JPA, providing CRUD (Create, Read, Update, Delete) operations for media file entities.

### Disclaimer
Please note that downloading videos from YouTube may infringe upon user copyrights. Use this tool responsibly and comply with all legal regulations and terms of service when downloading content.
