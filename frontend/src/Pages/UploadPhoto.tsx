import React, { useState } from 'react';
import { Fetch } from '../Utils/useFetch';
import { useParams } from "react-router-dom";

function UploadPhoto() {
    const [selectedFile, setSelectedFile] = useState(null);
    const params = useParams().id;

    const handleFileDrop = (event) => {
        event.preventDefault();
        const file = event.dataTransfer.files[0];
        if (file && (file.type === 'image/png' || file.type === 'image/jpeg')) {
            setSelectedFile(file);
        } else {
            alert('Por favor, selecione um arquivo PNG ou JPEG válido.');
        }
    };

    const handleFileUpload = async () => {
        if (!selectedFile) {
            alert('Por favor, selecione um arquivo.');
            return;
        }

        try {
            const formData = new FormData();
            formData.append('file', selectedFile);

            const response = await fetch(`http://localhost:8000/api/company/${params}/upload`, {
                method: 'POST',
                body: formData
            });

            if (response.status === 200) {
                alert("your image is uploaded")
                window.location.href = '/'
                console.log('Upload concluído');
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

    const handleFileSelect = (event) => {
        const file = event.target.files[0];
        if (file && (file.type === 'image/png' || file.type === 'image/jpeg')) {
            setSelectedFile(file);
        } else {
            alert('Por favor, selecione um arquivo PNG ou JPEG válido.');
        }
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <div
                style={{
                    width: '300px',
                    height: '300px',
                    border: '2px dashed gray',
                    borderRadius: '5px',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    cursor: 'pointer',
                    marginBottom: '20px',
                }}
                onDrop={handleFileDrop}
                onDragOver={handleDragOver}
            >
                {selectedFile ? (
                    <img
                        src={URL.createObjectURL(selectedFile)}
                        alt="Imagem selecionada"
                        style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                    />
                ) : (
                    <span>Arraste e solte a imagem aqui ou selecione um arquivo</span>
                )}
            </div>
            <input type="file" accept="image/png, image/jpeg" onChange={handleFileSelect} />
            <button onClick={handleFileUpload}>Enviar</button>
        </div>
    );
}

function FetchUploadFile(props:{selectedFiles}){
        const id = useParams().id
       if (props.selectedFiles.length > 0) {
            try {
                const formData = new FormData();
                    formData.append('files', props.selectedFiles[0].name);
                    console.log("file",props.selectedFiles[0].name)
                    const response = Fetch(`company/${id}/upload?fileName=${props.selectedFiles[0].name}`, 'POST');
                    console.log("AQUI",response)
                    return <> file no teu perfil </>
                // Upload bem-sucedido para todos os arquivos
            } catch (error) {
                console.log(error);
            }
        }
       return <> no file to update </>
}

export default UploadPhoto;