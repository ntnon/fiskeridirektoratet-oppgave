import { useFish } from "../hooks/useFish";
import { useState } from "react";
import FishTable from "./FishTable";
import FishFormModal from "./FishFormModal";

export default function FishList() {
  const { fishList, loading, error, refetch, deleteFishById } = useFish();
  const [sortConfig, setSortConfig] = useState({ key: null, direction: "asc" });
  const [editingFish, setEditingFish] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  if (loading)
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-lg text-gray-600">Loading fish data...</div>
      </div>
    );

  if (error)
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-lg text-red-600">Error: {error.message}</div>
      </div>
    );

  const handleAdd = () => {
    setEditingFish(null);
    setIsModalOpen(true);
  };

  const handleEdit = (fish) => {
    setEditingFish(fish);
    setIsModalOpen(true);
  };

  const handleModalClose = () => {
    setEditingFish(null);
    setIsModalOpen(false);
    refetch();
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this fish?",
    );
    if (!confirmed) return;

    try {
      await deleteFishById(id);
      await refetch();
    } catch (error) {
      console.error("Failed to delete fish:", error);
      alert("Failed to delete fish");
    }
  };

  const requestSort = (key) => {
    let direction = "asc";
    if (sortConfig.key === key && sortConfig.direction === "asc") {
      direction = "desc";
    }
    setSortConfig({ key, direction });
  };

  const sortedFish = [...fishList];
  if (sortConfig.key) {
    sortedFish.sort((a, b) => {
      const aVal = a[sortConfig.key];
      const bVal = b[sortConfig.key];

      if (aVal === null || aVal === undefined) return 1;
      if (bVal === null || bVal === undefined) return -1;

      if (typeof aVal === "string") {
        return sortConfig.direction === "asc"
          ? aVal.localeCompare(bVal)
          : bVal.localeCompare(aVal);
      }

      if (typeof aVal === "number") {
        return sortConfig.direction === "asc" ? aVal - bVal : bVal - aVal;
      }

      return 0;
    });
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="p-4 sm:p-8">
        <div className="mb-4 sm:mb-6 flex flex-col sm:flex-row justify-between items-start sm:items-center gap-3">
          <h1 className="text-2xl sm:text-3xl font-bold text-gray-900">
            Fish Registry
          </h1>
          <button
            onClick={handleAdd}
            className="px-4 sm:px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors font-medium text-sm sm:text-base"
          >
            Add New Fish
          </button>
        </div>

        {fishList.length > 0 ? (
          <FishTable
            fishList={sortedFish}
            sortConfig={sortConfig}
            onSort={requestSort}
            onEdit={handleEdit}
            onDelete={handleDelete}
            refetch={refetch}
          />
        ) : (
          <div className="bg-white rounded-lg shadow-md p-8 sm:p-12 text-center">
            <p className="text-gray-500 text-base sm:text-lg mb-4">
              No fish registered yet.
            </p>
            <button
              onClick={handleAdd}
              className="px-4 sm:px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors font-medium text-sm sm:text-base"
            >
              Add Your First Fish
            </button>
          </div>
        )}
      </div>

      {/* Modal for adding/editing */}
      {isModalOpen && (
        <FishFormModal fish={editingFish} onClose={handleModalClose} />
      )}
    </div>
  );
}
