-- Create FormatTable
CREATE TABLE FormatTable (
    FormatID INT PRIMARY KEY,
    FormatName VARCHAR(50) NOT NULL
);

-- Create VideoTable
CREATE TABLE VideoTable (
    VideoID INT PRIMARY KEY,
    Path VARCHAR(255) NOT NULL,
    Quality VARCHAR(20) NOT NULL,
    FormatID INT,
    FOREIGN KEY (FormatID) REFERENCES FormatTable(FormatID)
);

-- Create AudioTable
CREATE TABLE AudioTable (
    AudioID INT PRIMARY KEY,
    Path VARCHAR(255) NOT NULL,
    Quality VARCHAR(20) NOT NULL,
    FormatID INT,
    FOREIGN KEY (FormatID) REFERENCES FormatTable(FormatID)
);
