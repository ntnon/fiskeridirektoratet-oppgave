// src/services/fishService.js

const API_URL = "/api/fish";

// Helper function to check response
const handleResponse = async (response) => {
  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || "Request failed");
  }
  // If no content (204), return null
  return response.status === 204 ? null : response.json();
};

export const getAllFish = async () => {
  const response = await fetch(API_URL);
  return handleResponse(response);
};

export const getFishById = async (id) => {
  const response = await fetch(`${API_URL}/${id}`);
  return handleResponse(response);
};

export const createFish = async (fish) => {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(fish),
  });
  return handleResponse(response);
};

export const updateFish = async (id, fish) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(fish),
  });
  return handleResponse(response);
};

export const deleteFish = async (id) => {
  const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
  return handleResponse(response);
};

export const getAllSpecies = async () => {
  const response = await fetch(`${API_URL}/species`);
  return handleResponse(response);
};
