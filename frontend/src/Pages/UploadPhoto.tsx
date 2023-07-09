import React, { useState } from 'react';
import { Fetch,HOST } from '../Utils/useFetch';
import { useParams } from "react-router-dom";
//import '../Style/UploadFile.css'
import { Button } from "react-bootstrap";


function UploadPhoto() {
    const [selectedFiles, setSelectedFiles] = useState([]);
    const cid = useParams().id
    const handleFileDrop = (event) => {
        event.preventDefault();
        const files = Array.from(event.dataTransfer.files);
        const validFiles = filterValidFiles(files);
        setSelectedFiles((prevSelectedFiles) => [...prevSelectedFiles, ...validFiles.slice(0, 4)]);
    };

    const handleFileSelect = (event) => {
        const files = Array.from(event.target.files);
        const validFiles = filterValidFiles(files);
        setSelectedFiles((prevSelectedFiles) => [...prevSelectedFiles, ...validFiles.slice(0, 4)]);
    };

    const filterValidFiles = (files) =>
        files.filter((file) => file instanceof File && (file.type === 'image/png' || file.type === 'image/jpeg'));

    const handleFileUpload = async () => {
        if (selectedFiles.length === 0) {
            alert('please, select at least one file.');
            return;
        }

        try {
            const formData = new FormData();

            selectedFiles.forEach((file, index) => {
                formData.append('file', file); // Use 'files[]' como a chave para cada arquivo
            });

            console.log('FormData', formData);
            const response = await fetch(`${HOST}/company/${cid}/upload`, {
                method: 'POST',
                body: formData,
            });

            if (response.status === 200) {
                alert('Images sent!');
                window.location.href = '/';
            } else {
                console.error('Something went wrong', response.status);
            }
        } catch (error) {
            console.error('Something went wrong', error);
        }
    };

    const handleDragOver = (event) => {
        event.preventDefault();
    };

    return(
        <div className="image-uploader">
            <div
                className="image-grid"
                style={{
                    background: "#f8f9fa",
                    border: "2px dashed #ced4da",
                    borderRadius: "5px",
                    padding: "20px",
                    marginBottom: "20px",
                    display: "flex",
                    flexWrap: "wrap",
                    justifyContent: "center",
                }}
                onDrop={handleFileDrop}
                onDragOver={handleDragOver}
            >
                {[...Array(4)].map((_, index) => (
                    <div
                        key={index}
                        className="image-container"
                        style={{
                            width: "200px",
                            height: "200px",
                            border: "2px dashed gray",
                            borderRadius: "5px",
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            position: "relative",
                            margin: "10px",
                        }}
                    >
          <span className="file-icon" style={{ color: "gray", fontSize: "48px" }}>
            <i className="far fa-file"></i>
          </span>
                        {selectedFiles[index] ? (
                            <img
                                src={URL.createObjectURL(selectedFiles[index])}
                                alt={`Image ${index + 1}`}
                                style={{ width: "100%", height: "100%", objectFit: "cover" }}
                            />
                        ) : (
                            <span
                                className="drag-text"
                                style={{
                                    textAlign: "center",
                                    fontSize: "16px",
                                    fontWeight: "bold",
                                }}
                            >
              Drop your file here
            </span>
                        )}
                    </div>
                ))}
            </div>
            <input type="file" accept="image/png, image/jpeg" onChange={handleFileSelect} multiple />
            <div className="button-container">
                <Button variant="primary" size="lg" onClick={handleFileUpload}>
                    Send
                </Button>
            </div>
        </div>
    );
}
export default UploadPhoto;
