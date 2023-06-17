import React, { useState } from 'react';
import { Fetch } from '../Utils/useFetch';
import {useParams} from "react-router-dom";

function UploadPhoto() {
    const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
    const id = useParams().id
    const [selectedUpload, setSelectedUpload] = useState<boolean>(false);

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files.length > 0) {
            const files = Array.from(event.target.files);
            const validFiles = files.filter(
                file => file.type === 'image/png' || file.type === 'image/jpeg'
            );
            setSelectedFiles(validFiles);
        }
    };

    const handleDragOver = (event: React.DragEvent<HTMLDivElement>) => {
        event.preventDefault();
    };

    const handleDrop = (event: React.DragEvent<HTMLDivElement>) => {
        event.preventDefault();
        if (event.dataTransfer.files && event.dataTransfer.files.length > 0) {
            const files = Array.from(event.dataTransfer.files);
            const validFiles = files.filter(
                file => file.type === 'image/png' || file.type === 'image/jpeg'
            );
            setSelectedFiles(validFiles);
        }
    };

    const handleUpload = () => {
        setSelectedUpload(true)
    }


    return ( <>
        {
            selectedUpload?

                <FetchUploadFile selectedFiles={selectedFiles}/>

                :<div>
                    <div
                        onDragOver={handleDragOver}
                        onDrop={handleDrop}
                        style={{
                            border: '1px dashed gray',
                            padding: '2rem',
                            marginBottom: '1rem',
                        }}
                    >
                        Drop here your company picture
                    </div>
                    <input type="file" accept=".png,.jpg" onChange={handleFileChange} multiple />
                    <button onClick={handleUpload}>Send</button>
                 </div>
        }
        </>
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
