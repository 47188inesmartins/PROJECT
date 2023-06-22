import React, { useState } from 'react';
import { Fetch } from '../Utils/useFetch';
import { useParams } from "react-router-dom";

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
            alert('Por favor, selecione pelo menos um arquivo.');
            return;
        }

        try {
            const formData = new FormData();

            selectedFiles.forEach((file, index) => {
                formData.append('file', file); // Use 'files[]' como a chave para cada arquivo
            });

            console.log('FormData', formData);
            const response = await fetch(`http://localhost:8000/api/company/${cid}/upload`, {
                method: 'POST',
                body: formData,
            });

            if (response.status === 200) {
                alert('As imagens foram enviadas com sucesso');
                window.location.href = '/';
                console.log('Upload concluído', response);
            } else {
                console.error('Erro durante o upload:', response.status);
            }
        } catch (error) {
            console.error('Erro durante o upload:', error);
        }
    };

    const handleDragOver = (event) => {
        event.preventDefault();
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <div
                style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(2, 1fr)',
                    gridGap: '10px',
                    marginBottom: '20px',
                }}
                onDrop={handleFileDrop}
                onDragOver={handleDragOver}
            >
                {[...Array(4)].map((_, index) => (
                    <div
                        key={index}
                        style={{
                            width: '150px',
                            height: '150px',
                            border: '2px dashed gray',
                            borderRadius: '5px',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            cursor: 'pointer',
                        }}
                    >
                        {selectedFiles[index] ? (
                            <img
                                src={URL.createObjectURL(selectedFiles[index])}
                                alt={`Imagem ${index + 1}`}
                                style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                            />
                        ) : (
                            <span>Arraste e solte a imagem aqui ou selecione um arquivo</span>
                        )}
                    </div>
                ))}
            </div>
            <input type="file" accept="image/png, image/jpeg" onChange={handleFileSelect} multiple />
            <button onClick={handleFileUpload}>Enviar</button>
        </div>
    );
}
export default UploadPhoto;
