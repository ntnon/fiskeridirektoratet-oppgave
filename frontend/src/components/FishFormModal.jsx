import { useState, useEffect } from "react";
import { createFish, updateFish, getAllSpecies } from "../services/fishService";

export default function FishFormModal({ fish, onClose }) {
  const [formData, setFormData] = useState({
    name: "",
    species: "",
    lengthCm: "",
    weightKg: "",
  });
  const [availableSpecies, setAvailableSpecies] = useState([]);
  const [isCustomSpecies, setIsCustomSpecies] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  // Fetch available species on mount
  useEffect(() => {
    const fetchSpecies = async () => {
      try {
        const species = await getAllSpecies();
        setAvailableSpecies(species || []);
      } catch (err) {
        console.error("Failed to fetch species:", err);
      }
    };
    fetchSpecies();
  }, []);

  // Populate form when editing
  useEffect(() => {
    if (fish) {
      setFormData({
        name: fish.name || "",
        species: fish.species || "",
        lengthCm: fish.lengthCm || "",
        weightKg: fish.weightKg || "",
      });
      // Check if species exists in available species
      setIsCustomSpecies(
        fish.species && !availableSpecies.includes(fish.species),
      );
    } else {
      setFormData({ name: "", species: "", lengthCm: "", weightKg: "" });
      setIsCustomSpecies(false);
    }
  }, [fish, availableSpecies]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSpeciesChange = (e) => {
    const value = e.target.value;
    if (value === "__custom__") {
      setIsCustomSpecies(true);
      setFormData((prev) => ({ ...prev, species: "" }));
    } else {
      setIsCustomSpecies(false);
      setFormData((prev) => ({ ...prev, species: value }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setIsSubmitting(true);

    try {
      const fishData = {
        ...formData,
        lengthCm: formData.lengthCm ? parseFloat(formData.lengthCm) : null,
        weightKg: formData.weightKg ? parseFloat(formData.weightKg) : null,
      };

      if (fish?.id) {
        await updateFish(fish.id, fishData);
      } else {
        await createFish(fishData);
      }
      onClose();
    } catch (err) {
      setError(err.message || "Failed to save fish");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <>
      {/* Backdrop */}
      <div
        className="fixed inset-0 bg-black bg-opacity-50 z-40"
        onClick={onClose}
      />

      {/* Modal */}
      <div className="fixed inset-0 flex items-center justify-center z-50 p-4">
        <div className="bg-white rounded-lg shadow-xl w-full max-w-md">
          {/* Header */}
          <div className="px-6 py-4 border-b border-gray-200">
            <div className="flex items-center justify-between">
              <h2 className="text-xl font-semibold text-gray-900">
                {fish?.id ? "Edit Fish" : "Add New Fish"}
              </h2>
              <button
                onClick={onClose}
                className="text-gray-400 hover:text-gray-600 transition-colors"
              >
                <svg
                  className="w-6 h-6"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M6 18L18 6M6 6l12 12"
                  />
                </svg>
              </button>
            </div>
          </div>

          {/* Form */}
          <form onSubmit={handleSubmit} className="p-6">
            {error && (
              <div className="mb-4 p-3 bg-red-50 border border-red-200 rounded-lg text-red-700 text-sm">
                {error}
              </div>
            )}

            <div className="space-y-4">
              {/* Name */}
              <div>
                <label
                  htmlFor="name"
                  className="block text-sm font-medium text-gray-700 mb-1"
                >
                  Name *
                </label>
                <input
                  id="name"
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  required
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  placeholder="Enter fish name"
                />
              </div>

              {/* Species Dropdown */}
              <div>
                <label
                  htmlFor="species"
                  className="block text-sm font-medium text-gray-700 mb-1"
                >
                  Species *
                </label>
                {!isCustomSpecies ? (
                  <select
                    id="species"
                    name="species"
                    value={formData.species}
                    onChange={handleSpeciesChange}
                    required
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  >
                    <option value="">Select a species</option>
                    {availableSpecies.map((species) => (
                      <option key={species} value={species}>
                        {species}
                      </option>
                    ))}
                    <option value="__custom__">+ Add new species</option>
                  </select>
                ) : (
                  <div className="flex gap-2">
                    <input
                      type="text"
                      name="species"
                      value={formData.species}
                      onChange={handleChange}
                      required
                      className="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                      placeholder="Enter new species"
                      autoFocus
                    />
                    <button
                      type="button"
                      onClick={() => {
                        setIsCustomSpecies(false);
                        setFormData((prev) => ({ ...prev, species: "" }));
                      }}
                      className="px-3 py-2 text-gray-600 hover:text-gray-800 border border-gray-300 rounded-lg hover:bg-gray-50"
                    >
                      Cancel
                    </button>
                  </div>
                )}
              </div>

              {/* Length */}
              <div>
                <label
                  htmlFor="lengthCm"
                  className="block text-sm font-medium text-gray-700 mb-1"
                >
                  Length (cm)
                </label>
                <input
                  id="lengthCm"
                  type="number"
                  name="lengthCm"
                  value={formData.lengthCm}
                  onChange={handleChange}
                  step="0.1"
                  min="0"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  placeholder="Enter length in cm"
                />
              </div>

              {/* Weight */}
              <div>
                <label
                  htmlFor="weightKg"
                  className="block text-sm font-medium text-gray-700 mb-1"
                >
                  Weight (kg)
                </label>
                <input
                  id="weightKg"
                  type="number"
                  name="weightKg"
                  value={formData.weightKg}
                  onChange={handleChange}
                  step="0.01"
                  min="0"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  placeholder="Enter weight in kg"
                />
              </div>
            </div>

            {/* Footer */}
            <div className="flex justify-end gap-3 mt-6">
              <button
                type="button"
                onClick={onClose}
                className="px-4 py-2 text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors font-medium"
              >
                Cancel
              </button>
              <button
                type="submit"
                disabled={isSubmitting}
                className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors font-medium disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {isSubmitting
                  ? "Saving..."
                  : fish?.id
                    ? "Save Changes"
                    : "Add Fish"}
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
