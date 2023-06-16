import React, { useState } from 'react';
import {Fetch} from "./Utils/useFetch";

function UploadPhoto() {
    const [selectedFile, setSelectedFile] = useState<File | null>(null);

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files.length > 0) {
            const file = event.target.files[0];
            //so suporta ficheiros do tipo png e jpeg
            if (file.type === 'image/png' || file.type === 'image/jpeg') {
                setSelectedFile(file);
            } else {
               return <p> No supported File </p>
            }
        }
    };

    const handleDragOver = (event: React.DragEvent<HTMLDivElement>) => {
        event.preventDefault();
    };

    const handleDrop = (event: React.DragEvent<HTMLDivElement>) => {
        event.preventDefault();
        if (event.dataTransfer.files && event.dataTransfer.files.length > 0) {
            const file = event.dataTransfer.files[0];
            //so suporta ficheiros do tipo png e jpeg

            if (file.type === 'image/png' || file.type === 'image/jpeg') {
                setSelectedFile(file);
            } else {
                return <p> No supported File </p>
            }
        }
    };

    const handleUpload = async () => {
        if (selectedFile) {
            const formData = new FormData();
            formData.append('file', selectedFile);

            try {
                Fetch('company/1/photo','POST',formData)
            } catch (error) {
                console.log('Failed upload')
            }
        }
    };

    return (
        <div>
            <div
                onDragOver={handleDragOver}
                onDrop={handleDrop}
                style={{ border: '1px dashed gray', padding: '2rem', marginBottom: '1rem' }}
            >
                Drop here your company picture
            </div>
            <input type="file" accept=".png,.jpg" onChange={handleFileChange} />
            <button onClick={handleUpload}>Send</button>
        </div>
    );
}

export default UploadPhoto;
;
