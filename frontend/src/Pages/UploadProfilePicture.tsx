import {useParams} from "react-router-dom";
import * as React from "react"
import {useState} from "react";
import {UsersService} from "../Service/UsersService";
import {Layout, LayoutRight} from "./Layout";
import {AccessDenied} from "../Components/AccessDenied";
import {AddLayouts} from "../Components/AddLayouts";

export function UploadProfilePicture() {
    const [selectedFile, setSelectedFile] = useState(null);
    const params = useParams().id;

    const handleFileDrop = (event) => {
        event.preventDefault();
        const file = event.dataTransfer.files[0];
        if (file && (file.type === 'image/png' || file.type === 'image/jpeg')) {
            setSelectedFile(file);
        } else {
            alert('Please, select an PNG or JPEG valid file');
        }
    };

    const handleFileUpload = async () => {
        if (!selectedFile) {
            alert('Por favor, selecione um arquivo.');
            return;
        }
            const formData = new FormData();
            formData.append('file', selectedFile);
            UsersService.profilePicture(params,formData)
    };

    const handleDragOver = (event) => {
        event.preventDefault();
    };

    const handleFileSelect = (event) => {
        const file = event.target.files[0];
        if (file && (file.type === 'image/png' || file.type === 'image/jpeg')) {
            setSelectedFile(file);
        } else {
            alert('Please, select a valid file');
        }
    };

    const divElem = (
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
                        alt="Image"
                        style={{
                            width: '100%',
                            height: '100%',
                            objectFit: 'cover',
                            maxHeight: '300px',
                            maxWidth: '300px',
                        }}
                    />
                ) : (
                    <span>Drop an image here or select a file</span>
                )}
            </div>
            <input type="file" accept="image/png, image/jpeg" onChange={handleFileSelect} />
            <button onClick={handleFileUpload}>Send</button>
        </div>
    );
    return (
            <AddLayouts content={divElem}/>
    )

}
