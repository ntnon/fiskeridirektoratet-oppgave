export default function SortableHeader({
  label,
  mobileLabel,
  sortKey,
  onSort,
  getSortIndicator,
  sortConfig,
}) {
  const isSorted = sortConfig.key === sortKey;

  return (
    <th
      onClick={() => onSort(sortKey)}
      className={`
        px-1 sm:px-2 py-1 text-left text-[10px] sm:text-xs font-medium uppercase tracking-wider
        cursor-pointer transition-all select-none
        ${
          isSorted
            ? "bg-blue-50 text-blue-700 hover:bg-blue-100"
            : "text-gray-500 hover:bg-gray-50"
        }
      `}
    >
      <div className="flex items-center justify-between min-w-[40px] sm:min-w-[60px]">
        <span className="text-[10px] sm:text-xs">
          {mobileLabel ? (
            <>
              <span className="sm:hidden">{mobileLabel}</span>
              <span className="hidden sm:inline">{label}</span>
            </>
          ) : (
            label
          )}
        </span>
        <span
          className={`ml-1 text-[10px] sm:text-xs ${isSorted ? "text-blue-500" : "text-gray-400"}`}
        >
          {getSortIndicator(sortKey)}
        </span>
      </div>
    </th>
  );
}
