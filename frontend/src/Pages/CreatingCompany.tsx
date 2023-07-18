import React, {useEffect, useState} from "react";
import {Dropdown} from "react-bootstrap";
import "../Style/CreatingCompany.css";
import Cookies from 'js-cookie';
import {AddLayouts} from "../Components/AddLayouts";
import {CompanyServices} from "../Service/CompanyServices";


interface CompanyInputDto {
    name: string,
    street: string,
    phone: string,
    city: string,
    country: string,
    type: string,
    nif: string,
    description: string
}

export function CreatingCompany() {
    const [name, setName] = useState("");
    const [phone, setPhone] = useState("");
    const [street, setStreet] = useState("");
    const [nif, setNif] = useState("");
    const [city, setCity] = useState("");
    const [country, setCountry] = useState("");
    const [description, setDescription] = useState("");
    const categories = ["BEAUTY", "LIFESTYLE", "FITNESS", "BUSINESS", "OTHERS", "EDUCATION"];
    const [type, setType] = useState("");
    const [employeeEmails, setEmployeeEmails] = useState<string[]>([]);
    const daysOfWeek = ['MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'];

    const [schedule, setSchedule] = useState(
        daysOfWeek.map(day => ({
            weekDays: day,
            beginHour: '',
            endHour: '',
            intervalBegin:'',
            intervalEnd:''
        }))
    );
    const [lunchBreak, setLunchBreak] = useState({ beginHour: '', endHour: '' });
    const token =  Cookies.get('name');

    interface DayInputDto {
        weekDays: string;
        beginHour: string;
        endHour: string;
        intervalBegin: string;
        intervalEnd: string;
    }

    interface AddCompanyRequest {
        company: CompanyInputDto;
        emails: string[] | null;
        days: DayInputDto[];
    }

    const handleOptionClick = (value) => {
        setSelectedValue(
            value
            //  convertMinutesToHHMMSS(value)
        );
        setIsOpen(false);
    };


    const renderOptions = () => {
        const options = [];
        for (let i = 1; i <= 200; i++) {
            options.push(
                <div
                    key={i}
                    className="dropdown-option"
                    onClick={() => handleOptionClick(i)}
                >
                    {i} minutes
                </div>
            );
        }
        return options;
    };

    const handleCategorySelect = (category: string) => {
        setType(category);
    };

    const handleAddDetail = () => {
        setEmployeeEmails([...employeeEmails, ""]);
    };

    const handleDetailChange = (index: number, value: string) => {
        const updatedDetails = [...employeeEmails];
        updatedDetails[index] = value;
        setEmployeeEmails(updatedDetails);
        console.log(employeeEmails)
    };

    const handleRemoveDetail = (index: number) => {
        const updatedDetails = [...employeeEmails];
        updatedDetails.splice(index, 1);
        setEmployeeEmails(updatedDetails);
    };

    const companyData: CompanyInputDto = {
        name: name,
        street: street,
        phone: phone,
        city: city,
        country: country,
        type: type,
        nif: nif,
        description: description
    };



    function fetchCreateCompany() {
        CompanyServices.CreateCompany(selectedValue,companyData,employeeEmails,schedule)
    }

    const handleTimeChange = (index, field, value) => {
        setSchedule(prevSchedule => {
            if (index === null) {
                return prevSchedule.map(day => ({
                    ...day,
                    [field]: value
                }));
            } else {
                const updatedSchedule = [...prevSchedule];
                updatedSchedule[index][field] = value;
                return updatedSchedule;
            }
        });
        console.log(schedule)
    };


    const [isOpen, setIsOpen] = useState(false);
    const [selectedValue, setSelectedValue] = useState(null);


    const handleDropdownToggle = () => {
        setIsOpen(!isOpen);
    };

    const MAX_DESCRIPTION_LENGTH = 200;

    const divElem = (
        <div className="container rounded bg-white mt-5 mb-5">
            <div className="row">
                <div className="col-md-4 border-right">
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
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                />
                            </div>
                            <div className="col-md-12">
                                <label className="labels">Phone number</label>
                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="Phone number"
                                    value={phone}
                                    onChange={(e) => setPhone(e.target.value)}
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
                                    value={street}
                                    onChange={(e) => setStreet(e.target.value)}
                                />
                            </div>
                            <div className="mb-3 dropdown-space"></div>
                            <Dropdown>
                                <Dropdown.Toggle
                                    variant="primary"
                                    id="category-dropdown"
                                    className="form-control dropdown-toggle-text"
                                >
                                    {type || "Select category"}
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
                                    onChange={(e) =>setDescription(e.target.value)}
                                    maxLength={MAX_DESCRIPTION_LENGTH}
                                    rows={4}
                                />
                                <span className="text-muted">
                  {description.length}/{MAX_DESCRIPTION_LENGTH} characters
                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-md-4">
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
                        {employeeEmails.map((detail, index) => (
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
                <div className="col-md-4">
                    <div className="p-3 py-5">
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <h4 className="text-right" style={{ color: "black" }}>
                                Company Schedule
                            </h4>
                        </div>
                        <div className="row mt-3">
                            <div className="table-container">
                                <div>
                                    <table className="schedule-table">
                                        <tbody>
                                        {schedule.map((day, index) => (
                                            <tr key={index}>
                                                <th style={{ color: "black" }}>{day.weekDays}</th>
                                                <td>
                                                    <input
                                                        type="time"
                                                        value={day.beginHour}
                                                        onChange={(e) =>
                                                            handleTimeChange(index, "beginHour", e.target.value)
                                                        }
                                                        step="600"
                                                    />
                                                </td>
                                                <td>
                                                    <input
                                                        type="time"
                                                        value={day.endHour}
                                                        min={day.beginHour}
                                                        onChange={(e) =>
                                                            handleTimeChange(index, "endHour", e.target.value)
                                                        }
                                                        step="600"
                                                    />
                                                </td>
                                            </tr>
                                        ))}
                                        <tr>
                                            <th style={{ color: "black" }}>Lunch Break</th>
                                            <td>
                                                <input
                                                    type="time"
                                                    value={schedule[0].intervalBegin}
                                                    onChange={(e) =>
                                                        handleTimeChange(null,"intervalBegin", e.target.value)
                                                    }
                                                    step="600"
                                                />
                                            </td>
                                            <td>
                                                <input
                                                    type="time"
                                                    value={schedule[0].intervalEnd}
                                                    min={lunchBreak.beginHour}
                                                    onChange={(e) =>
                                                        handleTimeChange(null,"intervalEnd", e.target.value)
                                                    }
                                                    step="600"
                                                />
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <br />
                                <br />
                                <div className="btn-group" style={{ marginLeft: "10px", display: "flex", flexDirection: "column" }}>
                                    <div className="dropdown">
                                        <label style={{ color: 'black' }}>Duration between each block (in minutes):</label>
                                        <br />
                                        {isOpen && (
                                            <div className="dropdown-options-container">
                                                <div className="dropdown-options">{renderOptions()}</div>
                                            </div>
                                        )}
                                        <button className="dropdown-toggle" onClick={handleDropdownToggle}>
                                            {selectedValue ? `${selectedValue} minutes` : "Select a number"}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="mt-5 text-center">
                            <button className="btn btn-outline-dark btn-lg px-5" type="submit"
                                    onClick={fetchCreateCompany}>create company
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

    return <AddLayouts content={divElem}/>
}