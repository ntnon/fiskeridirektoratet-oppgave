import SortableHeader from "./SortableHeader";

export default function FishTable({
  fishList,
  sortConfig,
  onSort,
  onEdit,
  onDelete,
}) {
  const getSortIndicator = (key) => {
    if (sortConfig.key !== key) return "";
    return sortConfig.direction === "asc" ? "↑" : "↓";
  };

  return (
    <div className="flex justify-center">
      <div className="w-full max-w-4xl">
        <div className="overflow-x-auto">
          <table className="w-full bg-white rounded-lg overflow-hidden shadow-md">
            <thead className="bg-gray-50">
              <tr>
                <SortableHeader
                  label="Name"
                  sortKey="name"
                  onSort={onSort}
                  getSortIndicator={getSortIndicator}
                  sortConfig={sortConfig}
                />
                <SortableHeader
                  label="Species"
                  sortKey="species"
                  onSort={onSort}
                  getSortIndicator={getSortIndicator}
                  sortConfig={sortConfig}
                />
                <SortableHeader
                  label="Length"
                  mobileLabel="Len"
                  sortKey="lengthCm"
                  onSort={onSort}
                  getSortIndicator={getSortIndicator}
                  sortConfig={sortConfig}
                />
                <SortableHeader
                  label="Weight"
                  mobileLabel="Wgt"
                  sortKey="weightKg"
                  onSort={onSort}
                  getSortIndicator={getSortIndicator}
                  sortConfig={sortConfig}
                />
                <th className="px-1 sm:px-2 py-1 text-left text-[10px] sm:text-xs font-medium text-gray-500 uppercase tracking-wider">
                  <span className="hidden sm:inline">Actions</span>
                  <span className="sm:hidden">Act</span>
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {fishList.map((fish, index) => (
                <tr
                  key={fish.id}
                  onClick={() => onEdit(fish)}
                  className={`${
                    index % 2 === 0 ? "bg-white" : "bg-gray-50"
                  } hover:bg-blue-50 cursor-pointer transition-colors`}
                >
                  <td className="px-1 sm:px-2 py-1 whitespace-nowrap">
                    <span className="text-[11px] sm:text-xs text-gray-900">
                      {fish.name || <span className="text-gray-400">—</span>}
                    </span>
                  </td>
                  <td className="px-1 sm:px-2 py-1 whitespace-nowrap">
                    <span className="text-[11px] sm:text-xs text-gray-900">
                      {fish.species || <span className="text-gray-400">—</span>}
                    </span>
                  </td>
                  <td className="px-1 sm:px-2 py-1 whitespace-nowrap">
                    <span className="text-[11px] sm:text-xs text-gray-900">
                      {fish.lengthCm ? (
                        <>
                          {fish.lengthCm}
                          <span className="hidden sm:inline"> cm</span>
                        </>
                      ) : (
                        <span className="text-gray-400">—</span>
                      )}
                    </span>
                  </td>
                  <td className="px-1 sm:px-2 py-1 whitespace-nowrap">
                    <span className="text-[11px] sm:text-xs text-gray-900">
                      {fish.weightKg ? (
                        <>
                          {fish.weightKg}
                          <span className="hidden sm:inline"> kg</span>
                        </>
                      ) : (
                        <span className="text-gray-400">—</span>
                      )}
                    </span>
                  </td>
                  <td
                    className="px-1 sm:px-2 py-1 whitespace-nowrap"
                    onClick={(e) => e.stopPropagation()}
                  >
                    <button
                      onClick={() => onDelete(fish.id)}
                      className="text-[11px] sm:text-xs text-red-600 hover:text-red-900 transition-colors font-medium"
                    >
                      <span className="hidden sm:inline">Delete</span>
                      <span className="sm:hidden">Del</span>
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
