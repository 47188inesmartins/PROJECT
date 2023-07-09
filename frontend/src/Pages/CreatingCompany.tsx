import React, { useState } from "react";
import {Fetch, SimpleFetch} from "../Utils/useFetch";
import { Dropdown, Button } from "react-bootstrap";
import "../Style/CreatingCompany.css";

interface CompanyInputDto {
    nif: string;
    address: string;
    name: string;
    type: string;
    description: string;
}

export function CreatingCompany() {
    const [companyName, setCompanyName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [address, setAddress] = useState("");
    const [nif, setNif] = useState("");
    const [city, setCity] = useState("");
    const [country, setCountry] = useState("");
    const [description, setDescription] = useState("");
    const categories = ["BEAUTY", "LIFESTYLE", "FITNESS", "BUSINESS", "OTHERS", "EDUCATION"];
    const [selectedCategory, setSelectedCategory] = useState("");
    const [additionalDetails, setAdditionalDetails] = useState<string[]>([]);
    const [create, setCreate] = useState<string[]>([]);

    const handleCategorySelect = (category: string) => {
        setSelectedCategory(category);
    };

    const handleAddDetail = () => {
        setAdditionalDetails([...additionalDetails, ""]);
    };

    const handleDetailChange = (index: number, value: string) => {
        const updatedDetails = [...additionalDetails];
        updatedDetails[index] = value;
        setAdditionalDetails(updatedDetails);
    };

    const handleRemoveDetail = (index: number) => {
        const updatedDetails = [...additionalDetails];
        updatedDetails.splice(index, 1);
        setAdditionalDetails(updatedDetails);
    };

    const companyData: CompanyInputDto = {
        nif: nif,
        address: address,
        name: companyName,
        type: selectedCategory,
        description: description,
    };


    function FetchCreateCompany() {
        const resp = Fetch("/company", "POST", companyData).response;

        if (!resp) return <p>...loading...</p>;

        if (resp.status) {
            setCreate(false);
            window.location.href = `/`;
            return <></>;
        }

        if (resp) {
            const companyId = resp.id;
            window.location.href = `/company/${companyId}/schedule`;
            return <></>;
        }
    }

    const isFormValid = () => {
        return (
            companyName.length >= 3 &&
            selectedCategory.length >= 3 &&
            address.length >= 3 &&
            nif.length === 9 &&
            description.length >= 3
        );
    };

    const MAX_DESCRIPTION_LENGTH = 200;

    return (
        <div className="container rounded bg-white mt-5 mb-5">
            <div className="row">
                <div className="col-md-5 border-right">
                    <div className="p-3 py-5">
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <h4 className="text-right" style={{ color: "black" }}>
                                Register your company
                            </h4>
                        </div>
                        <div className="row mt-3">
                            <div className="col-md-12">
                                <label className="labels">Company Name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Company Name"
                                    value={companyName}
                                    onChange={(e) => setCompanyName(e.target.value)}
                                />
                            </div>
                            <div className="col-md-12">
                                <label className="labels">Phone number</label>
                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="Phone number"
                                    value={phoneNumber}
                                    onChange={(e) => setPhoneNumber(e.target.value)}
                                />
                            </div>
                            <div className="col-md-12">
                                <label className="labels">NIF</label>
                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="NIF"
                                    value={nif}
                                    onChange={(e) => setNif(e.target.value)}
                                />
                            </div>
                            <div className="col-md-12">
                                <label className="labels">Country</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Country"
                                    value={country}
                                    onChange={(e) => setCountry(e.target.value)}
                                />
                            </div>
                            <div className="col-md-12">
                                <label className="labels">City</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="City"
                                    value={city}
                                    onChange={(e) => setCity(e.target.value)}
                                />
                            </div>
                            <div className="col-md-12">
                                <label className="labels">Address</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Address"
                                    value={address}
                                    onChange={(e) => setAddress(e.target.value)}
                                />
                            </div>
                            <div className="mb-3 dropdown-space"></div>
                            <Dropdown>
                                <Dropdown.Toggle
                                    variant="primary"
                                    id="category-dropdown"
                                    className="form-control dropdown-toggle-text"
                                >
                                    {selectedCategory || "Select category"}
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    {categories.map((category) => (
                                        <Dropdown.Item
                                            key={category}
                                            onClick={() => handleCategorySelect(category)}
                                        >
                                            {category}
                                        </Dropdown.Item>
                                    ))}
                                </Dropdown.Menu>
                            </Dropdown>
                            <div className="col-md-12">
                                <label className="labels">Description</label>
                                <textarea
                                    className="form-control description-input"
                                    placeholder="Description"
                                    value={description}
                                    onChange={(e) => setDescription(e.target.value)}
                                    maxLength={MAX_DESCRIPTION_LENGTH}
                                    rows={4}
                                />
                                <span className="text-muted">
                                    {description.length}/{MAX_DESCRIPTION_LENGTH} characters
                                </span>
                            </div>
                        </div>
                        <div className="mt-5 text-center">
                            <button
                                className="btn btn-primary profile-button"
                                type="button"
                                disabled={!isFormValid()}
                                onClick={fetchCreateCompany}
                            >
                                Create company
                            </button>
                        </div>
                    </div>
                </div>
                <div className="col-md-5">
                    <div className="p-3 py-5">
                        <div className="d-flex justify-content-between align-items-center experience">
                            <span style={{ color: "black" }}>
                                Add employees to your company
                            </span>
                            <button
                                className="btn btn-outline-primary btn-sm"
                                onClick={handleAddDetail}
                            >
                                <i className="fa fa-plus"></i>&nbsp; Employee
                            </button>
                        </div>
                        <br />
                        {additionalDetails.map((detail, index) => (
                            <div className="col-md-12" key={index}>
                                <label className="labels">Employee's emails</label>
                                <div className="input-group">
                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Additional Detail"
                                        value={detail}
                                        onChange={(e) => handleDetailChange(index, e.target.value)}
                                    />
                                    <div className="input-group-append">
                                        <button
                                            className="btn btn-outline-danger"
                                            type="button"
                                            onClick={() => handleRemoveDetail(index)}
                                        >
                                            <i className="fa fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}