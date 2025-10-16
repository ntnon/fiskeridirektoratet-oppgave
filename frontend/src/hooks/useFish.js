// src/hooks/useFish.js
import { useState, useEffect } from "react";
import { getAllFish, deleteFish } from "../services/fishService";

export const useFish = () => {
  const [fishList, setFishList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchFish = async () => {
    setLoading(true);
    try {
      const data = await getAllFish();
      setFishList(data);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  const deleteFishById = async (id) => {
    try {
      await deleteFish(id);
      // Optionally update local state immediately for faster UX
      setFishList((prev) => prev.filter((fish) => fish.id !== id));
    } catch (err) {
      setError(err);
      throw err; // Re-throw to let the component handle it
    }
  };

  useEffect(() => {
    fetchFish();
  }, []);

  return {
    fishList,
    loading,
    error,
    refetch: fetchFish,
    deleteFishById,
  };
};
